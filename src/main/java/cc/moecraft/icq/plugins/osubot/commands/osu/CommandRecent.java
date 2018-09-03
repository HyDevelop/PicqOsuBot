package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.browser.browsers.RecentBrowser;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.RecentScoreNotEnoughException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UnsupportedModeException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;
import cn.hutool.http.HttpException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandRecent extends OsuCommandBase
{
    public CommandRecent()
    {
        super(0, "recent");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws Exception
    {
        try
        {
            long startTime = System.currentTimeMillis();

            String username = getDatabasedUsername(args, 0, user);
            event.respond("正在查询最近成绩... 请稍后ww");

            File imageFile = Main.getBrowserManager().getBrowser(RecentBrowser.class).render(username);
            logComplete(user.getInfo().getNickname(), username, startTime);

            return getImageMessage(imageFile);
        }
        catch (RecentScoreNotEnoughException e)
        {
            return "你的最近成绩不够哦ww 快去打图!!";
        }
        catch (RuntimeException e)
        {
            if (e.getCause() instanceof UnsupportedModeException) throw ((UnsupportedModeException) e.getCause());
            throw e;
        }
        catch (UnsupportedModeException e)
        {
            return e.getMode() + " 模式暂时不支持PP计算";
        }
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("recent", "最近成绩", "ercent");
    }
}
