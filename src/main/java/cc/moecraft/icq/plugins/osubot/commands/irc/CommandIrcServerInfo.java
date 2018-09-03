package cc.moecraft.icq.plugins.osubot.commands.irc;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import org.pircbotx.PircBotX;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/15 创建!
 * Created by Hykilpikonna on 2018/08/15!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandIrcServerInfo implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        PircBotX irc = Main.getPircSender().getPircBotX();
        return new MessageBuilder().add("IRC 服务器信息: ").newLine()
                .add("- ").add(irc.getServerInfo())
                .toString();
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("irc-server");
    }
}
