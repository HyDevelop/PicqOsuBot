package cc.moecraft.icq.plugins.osubot.commands.setid;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
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
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.icq.plugins.osubot.Main.CONTACT_INFO;

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
    public CommandSetId()
    {
        super(1, "setid");
    }

    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws HttpException, UserNotFoundException
    {
    }

    @Override
    public String help(String command)
    {
        return "用法: /" + command + " [用户名]\n" +
                "注意: 绑定osuId的时候需要登录游戏接收验证码!";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("setid", "bind");
    }
}
