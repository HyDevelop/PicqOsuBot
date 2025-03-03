package cc.moecraft.icq.plugins.osubot.commands.setid;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;
import cc.moecraft.utils.MathUtils;
import cn.hutool.http.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.icq.plugins.osubot.Main.CONTACT_INFO;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandSetId extends OsuCommandBase
{
    private Map<Long, PendingDataSet> confirmMap = new HashMap<>();

    public CommandSetId()
    {
        super(1, "setid");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws HttpException, UserNotFoundException
    {
        long currentTime = System.currentTimeMillis();
        if (args.get(0).equals("confirm"))
        {
            if (!confirmMap.containsKey(user.getId())) return Messages.requestNotFound(command);

            PendingDataSet dataSet = confirmMap.get(user.getId());
            if (!isConfirmTimeValid(dataSet)) return Messages.confirmTimeInvalid(command);

            // 保存数据库并发送验证码
            dataSet.getUserSettings().save();
            Main.getPircSender().send(dataSet.getUserData().getUsername(), Messages.verificationCode(dataSet.getUserSettings().getVerificationCode()));
            logComplete("用户 " + user.getInfo().getNickname() + " 确认了SetID请求");
            this.confirmMap.remove(user.getId());
            return Messages.verificationNeeded(dataSet);
        }
        else
        {
            // 获取QQ上用户提供的信息
            String username = ArrayUtils.getTheRestArgsAsString(args, 0);
            OWAUserData userData = OWAUtils.getUserData(username);

            // 验证数据库内是否已存在QQ id
            HoUserSettings qqIdUserSettings = ServiceInstanceManager.get(HoUserSettingsServiceImpl.class).findByQq(user.getId());
            if (qqIdUserSettings != null)
            {
                return Messages.qqIdAlreadyVerified(qqIdUserSettings);
            }

            // 验证数据库内是否已存在osu id
            HoUserSettings osuIdUserSettings = ServiceInstanceManager.get(HoUserSettingsServiceImpl.class).findByOsu(userData.getId());
            if (osuIdUserSettings != null)
            {
                if (osuIdUserSettings.getVerificationState().equals(VerificationStates.VERIFIED)) return Messages.osuIdAlreadyVerified(osuIdUserSettings);
                if (isTimeValid(osuIdUserSettings)) return Messages.requestedAndValidTime(command);
                return Messages.requestedButInvalidTime(osuIdUserSettings, command);
            }

            // 数据库内不存在
            String verificationCode = String.valueOf(MathUtils.getRandom(10000000, 99999999));
            HoUserSettings userSettings = new HoUserSettings();
            userSettings.setOsuId(userData.getId());
            userSettings.setOsuName(userData.getUsername());
            userSettings.setQqId(user.getId());
            userSettings.setDefaultMode(userData.playmode);
            userSettings.setVerificationCode(verificationCode);
            userSettings.setVerificationState(VerificationStates.VERIFYING);
            userSettings.setVerificationTime(currentTime);

            // 放入缓存
            logComplete("用户 " + user.getInfo().getNickname() + " 请求了SetID.");
            this.confirmMap.put(user.getId(), new PendingDataSet(user, userData, userSettings, currentTime));
            return Messages.confirmNeeded(userSettings, command);
        }
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]\n" +
                "注意: 绑定osuId的时候需要登录游戏接收验证码!";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("setid", "bind");
    }

    @Data @AllArgsConstructor @ToString(callSuper = true)
    private static class PendingDataSet
    {
        User user;
        OWAUserData userData;
        HoUserSettings userSettings;
        long startTime;
    }

    private static class Messages
    {
        private static String osuIdAlreadyVerified(HoUserSettings userSettings)
        {
            return new MessageBuilder()
                    .add("玩家 ").add(userSettings.getOsuName()).add(" 已经绑定过了啦...").newLine()
                    .add("他绑定的QQ是").add(userSettings.getQqId()).add("!").newLine()
                    .add("要解绑或绑定其他OsuID的话, 联系: ").add(CONTACT_INFO).add("啦!")
                    .toString();
        }

        private static String qqIdAlreadyVerified(HoUserSettings userSettings)
        {
            return new MessageBuilder()
                    .add("你已经绑定过了啦_(:з」∠)_").newLine()
                    .add("你绑定的OsuID是").add(userSettings.getOsuName()).add("哦!").newLine()
                    .add("要解绑或绑定其他OsuID的话, 联系: ").add(CONTACT_INFO).add("啦!")
                    .toString();
        }

        private static String requestedButInvalidTime(HoUserSettings userSettings, String command)
        {
            return new MessageBuilder()
                    .add("你要绑定的ID ").add(userSettings.getOsuName()).add(" 已经请求过").add(command).add("了...").newLine()
                    .add("但是他没有在30分钟内验证完_(:з」∠)_").newLine()
                    .add("为了防止游戏内聊天刷屏...").newLine()
                    .add("要重新绑定的话请联系: ").add(CONTACT_INFO).add("!")
                    .toString();
        }

        private static String requestedAndValidTime(String command)
        {
            return new MessageBuilder()
                    .add("已经输过setid啦... 看看上面那一段!").newLine()
                    .add("如果已经登录进游戏了就 -").add(command).add(" confirm好啦w")
                    .toString();
        }

        private static String confirmNeeded(HoUserSettings userSettings, String command)
        {
            return new MessageBuilder()
                    .add("注意: 一个QQ只能申请一次绑定,").newLine()
                    .add("").add(userSettings.getOsuName()).add("必须是你的真实ID哦!").newLine()
                    .add("还有必须确认前登录游戏才能收到验证码!").newLine()
                    .add("请输入 -").add(command).add(" confirm 确认")
                    .toString();
        }

        private static String verificationNeeded(PendingDataSet dataSet)
        {
            return new MessageBuilder()
                    .add("已将验证码发送给: ").add(dataSet.getUserData().getUsername()).newLine()
                    .add("请在游戏内接收验证码w").newLine()
                    .add("然后在QQ上输入指令 -verify [验证码] 就能完成绑定啦!")
                    .toString();
        }

        private static String verificationCode(String verificationCode)
        {
            return verificationCode + " _(:з」∠)_";
        }

        private static String confirmTimeInvalid(String command)
        {
            return new MessageBuilder()
                    .add("confirm时间超过两分钟啦_(:з」∠)_").newLine()
                    .add("为了安全, 请重新").add(command).add(".")
                    .toString();
        }

        private static String requestNotFound(String command)
        {
            return new MessageBuilder()
                    .add("还没有").add(command).add(", 打算怎么confirm呢?")
                    .toString();
        }
    }

    static boolean isTimeValid(HoUserSettings userSettings)
    {
        return System.currentTimeMillis() - userSettings.getVerificationTime() < 30 * 60 * 1000;
    }

    private static boolean isConfirmTimeValid(PendingDataSet dataSet)
    {
        return System.currentTimeMillis() - dataSet.startTime < 120 * 1000;
    }
}
