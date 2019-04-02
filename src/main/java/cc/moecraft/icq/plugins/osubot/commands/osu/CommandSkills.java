package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.browser.browsers.SkillsBrowser;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandSkills extends OsuCommandBase
{
    public CommandSkills()
    {
        super(0, "skill");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws Exception
    {
        if (Main.disableStatsSkills) return "";
        
        long startTime = System.currentTimeMillis();

        String username = getDatabasedUsername(args, 0, user);
        event.respond("正在查询能力评分... 请稍后ww");

        File imageFile = Main.getBrowserManager().getBrowser(SkillsBrowser.class).render(username);
        logComplete(user.getInfo().getNickname(), username, startTime);

        return getImageMessage(imageFile);
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("skills", "skill", "能力评分");
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]" + "\n注意: 目前能力评分只支持osu!STD模式哦!";
    }
}
