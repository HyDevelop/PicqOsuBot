package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.utils.ResourceFileUtils;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;

import java.io.File;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/09 创建!
 * Created by Hykilpikonna on 2018/08/09!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandHelp implements EverywhereCommand
{

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList)
    {
        Main.getInstance().getLogger().log("HELP - 用户 " + user.getInfo().getNickname() + " 查询了帮助.");
        File helpFile = ResourceFileUtils.getCachedResourceFile("help/Ver1.4-crop.jpg");
        if (helpFile == null) return null;
        return new ComponentImage("file://" + helpFile.getAbsolutePath()).toString();
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("help", "帮助", "?");
    }
}
