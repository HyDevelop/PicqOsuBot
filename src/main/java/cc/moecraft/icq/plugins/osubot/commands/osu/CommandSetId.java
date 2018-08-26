package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.utils.MathUtils;
import cc.moecraft.icq.plugins.permissions.Permissions;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;
import cn.hutool.http.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Integer, PendingDataSet> pendingDataMap = new HashMap<>();

    public CommandSetId()
    {
        super(1, "setid");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws HttpException, UserNotFoundException
    {
        if (!Permissions.verifyAndSendText(event, user.getId(), "osubot.demo.setid")) return null;
        long startTime = System.currentTimeMillis();
        String username = ArrayUtils.getTheRestArgsAsString(args, 0);

        try
        {
            int verificationCodeReceived = Integer.parseInt(username);
            if (pendingDataMap.containsKey(verificationCodeReceived))
            {
                PendingDataSet pendingDataSet = pendingDataMap.get(verificationCodeReceived);
                if (startTime - pendingDataSet.startTime > 10 * 60 * 1000)
                {
                    Main.getDatabase().putUser(pendingDataSet.user.getId(), pendingDataSet.getUserData());
                }
                else return "接收验证码时间太长了! (超过10分钟了)\n为了保护你的账号不被别人暴力破解绑定, 请重试_(:з」∠)_";
            }
        }
        catch (NumberFormatException ignored) {}
        catch (SQLException e)
        {
            e.printStackTrace();
            return "用户验证 - 数据库访问错误, 绑定ID失败了_(:з」∠)_\n已记录报错.";
        }

        OWAUserData userData = OWAUtils.getUserData(username);
        int verificationCode = MathUtils.getRandom(10000000, 99999999);

        pendingDataMap.put(verificationCode, new PendingDataSet(user, userData, startTime));

        try
        {
            String verificationText = localizeVerificationCode(verificationCode, userData.getCountry().getName());

            Main.getPircSender().send(userData.getUsername().replace(" ", "_"), verificationText);

            return new MessageBuilder().add("用户验证: ").newLine()
                    .add("- 已将验证码发送给: ").add(username).newLine()
                    .add("- 请在游戏内接收验证码w").newLine()
                    .add("- 在QQ上输入/").add(command).add(" [验证码] 即可绑定ID.")
                    .toString();
        }
        catch (LanguageNotFoundException e)
        {
            return "国家" + userData.getCountry().getName() + "不支持绑定! 如果想要支持的话请联系me@hydev.org给这个国家的语言翻译一句话.";
        }
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]\n" +
                "注意: 绑定osu用户名的时候需要在游戏内接收验证码";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("setid", "bind");
    }

    @Data @AllArgsConstructor
    private static class PendingDataSet
    {
        User user;
        OWAUserData userData;
        long startTime;
    }

    private static String localizeVerificationCode(int verificationCode, String lang) throws LanguageNotFoundException
    {
        switch (lang)
        {
            case "CN":
                return "验证码: " + verificationCode + ". " +
                        "如果您没有使用此功能, 请无视掉这条消息, 我们不会给您发送更多消息.";
            case "US": case "UK": case "CA":
                return "The verification code you requested is: " + verificationCode + ". " +
                        "If you did not use this feature, please ignore this message. We will no longer send any more messages to you.";
            default:
                throw new LanguageNotFoundException(lang);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data @AllArgsConstructor
    private static class LanguageNotFoundException extends Exception
    {
        private String requestedLanguage;
    }
}
