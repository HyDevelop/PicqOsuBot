package cc.moecraft.icq.plugins.osubot.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.osu.OsuHtmlUtils;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.pojo.UserData;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandStats implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> arrayList)
    {
        if (arrayList.size() < 1) return "用法: /" + command + " [用户名]";

        event.respond("正在查询... 请稍后ww");
        long startTime = System.currentTimeMillis();

        String username = ArrayUtils.getTheRestArgsAsString(arrayList, 0);
        Long userId;
        try
        {
            UserData userData = OsuHtmlUtils.getUserData(username);
            userId = userData.getId();
        }
        catch (UserNotFoundException | NullPointerException e1)
        {
            return "未找到用户: " + username;
        }

        File imageFile = Main.getInstance().getUserStatsImageBrowserManager().getBrowser().getUserStatsImage(userId);
        if (imageFile == null) return new MessageBuilder()
                .add("查询失败, 渲染图片的时候返回了null. 请联系")
                .add(new ComponentAt(565656L)).add(".").toString();
        event.respond(new MessageBuilder()
                .add(new ComponentImage("file://" + imageFile.getAbsolutePath())).toString());

        Main.getInstance().getLogger().log("STATS - 用户 " + user.getInfo().getNickname() + " 查询 " + username + " 完毕, 耗时" + (System.currentTimeMillis() - startTime) + "ms");

        return null;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("stats", "me", "home", "stat");
    }
}
