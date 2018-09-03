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
    public CommandVerify()
    {
        super(1, "verify");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws UserNotFoundException, HttpException, IOException
    {
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
}
