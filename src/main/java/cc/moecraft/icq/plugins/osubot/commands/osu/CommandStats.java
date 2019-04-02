package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.browser.browsers.StatsBrowser;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.icq.user.User;

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
public class CommandStats extends OsuCommandBase
{
    public CommandStats()
    {
        super(0, "stats");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws Exception
    {
        if (Main.disableStatsSkills) return "";

        long startTime = System.currentTimeMillis();

        String username = getDatabasedUsername(args, 0, user);
        event.respond("正在查询... 请稍后ww");

        OWAUserData userData = OWAUtils.getUserData(username);
        Long userId = userData.getId();

        File imageFile = Main.getBrowserManager().getBrowser(StatsBrowser.class).render(userId);
        logComplete(user.getInfo().getNickname(), username, startTime);

        return getImageMessage(imageFile);
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("stats", "home", "stat");
    }
}
