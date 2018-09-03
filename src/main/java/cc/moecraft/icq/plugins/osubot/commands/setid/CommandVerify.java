package cc.moecraft.icq.plugins.osubot.commands.setid;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;
import cn.hutool.http.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.icq.plugins.osubot.Main.CONTACT_INFO;

/**
 * 此类由 Hykilpikonna 在 2018/09/02 创建!
 * Created by Hykilpikonna on 2018/09/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandVerify extends OsuCommandBase
{
    private Map<Long, Long> qqTimeMap = new HashMap<>();

    public CommandVerify()
    {
        super(1, "verify");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws UserNotFoundException, HttpException, IOException
    {
        long currentTime = System.currentTimeMillis();

        // 验证长度
        String verificationCodeFromUser = ArrayUtils.getTheRestArgsAsString(args, 0);
        if (verificationCodeFromUser.length() != 8) return Messages.invalidLength(verificationCodeFromUser);

        // 获取数据库对象
        HoUserSettings userSettings = ServiceInstanceManager.get(HoUserSettingsServiceImpl.class).findByQq(user.getId());
        if (userSettings == null) return Messages.notRequested();
        if (userSettings.getVerificationState().equals(VerificationStates.VERIFIED)) return Messages.alreadyVerified();
        if (!CommandSetId.isTimeValid(userSettings)) return Messages.timeout();
    }

    @Override
    public String help(String command)
    {
        return "验证绑定ID: /verify [验证码]";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("verify");
    }

    private static class Messages
    {
        private static String success(HoUserSettings userSettings)
        {
            return "验证成功啦! 已绑定ID: " + userSettings.getOsuName();
        }

        private static String invalidVerificationCode()
        {
            return "验证码不对哦! 请10秒后再试_(:з」∠)_";
        }

        private static String notRequested()
        {
            return "你还没setid怎么验证哇...";
        }

        private static String alreadyVerified()
        {
            return "你已经验证过了啦w";
        }

        private static String invalidLength(String verificationCodeFromUser)
        {
            return new MessageBuilder()
                    .add("输错啦w 应该是8位数字的!").newLine()
                    .add("你输的验证码是").add(verificationCodeFromUser.length()).add("位!!")
                    .toString();
        }

        private static String timeout()
        {
            return new MessageBuilder()
                    .add("这个验证码已经超过30分钟了...").newLine()
                    .add("想重新绑定的话联系 ").add(CONTACT_INFO).add(" 吧!")
                    .toString();
        }
    }
}
