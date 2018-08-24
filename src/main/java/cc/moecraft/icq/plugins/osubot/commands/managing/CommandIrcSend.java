package cc.moecraft.icq.plugins.osubot.commands.managing;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.permissions.Permissions;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/20 创建!
 * Created by Hykilpikonna on 2018/08/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandIrcSend implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() < 2) return "向IRC发送消息: /irc-send [目标] [消息...]";
        if (!Permissions.verifyAndSendText(event, sender.getId(), "osubot.irc.send")) return null;

        String target = args.get(0);
        String message = ArrayUtils.getTheRestArgsAsString(args, 1);

        Main.getPircSender().send(target, message);
        return "成功!";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("irc-send");
    }
}
