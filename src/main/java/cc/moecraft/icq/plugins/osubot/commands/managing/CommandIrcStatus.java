package cc.moecraft.icq.plugins.osubot.commands.managing;

import cc.moecraft.icq.PicqBotX;
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
public class CommandIrcStatus implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        PircBotX irc = Main.getPircSender().getPircBotX();
        return new MessageBuilder().add("IRC 客户端: ").newLine()
                .add("- 服务器: ").add(irc.getServerHostname()).add(" 端口 ").add(irc.getServerPort()).newLine()
                .add("- 用户: ").add(irc.getNick()).newLine()
                .add("- 连接状态: ").add(irc.getState())
                .toString();
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("irc-status");
    }
}
