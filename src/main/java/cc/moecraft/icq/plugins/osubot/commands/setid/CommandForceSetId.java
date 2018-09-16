package cc.moecraft.icq.plugins.osubot.commands.setid;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.icq.plugins.permissions.Permissions;
import cc.moecraft.icq.plugins.permissions.utils.CommandUtils;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/09/04 创建!
 * Created by Hykilpikonna on 2018/09/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandForceSetId implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        System.out.println("收到setid--force");
        try
        {
            if (!Permissions.verifyAndSendText(event, sender.getId(), "osubot.setid.force")) return null;
            if (args.size() < 2) return "用法: /setid--force [QQ/@] [osu名]";
            try
            {
                Long qq = CommandUtils.getQqWithStringAndSendMessage(event, args.get(0));
                String username = ArrayUtils.getTheRestArgsAsString(args, 1);
                OWAUserData userData = OWAUtils.getUserData(username);
                HoUserSettings userSettings = new HoUserSettings();
                userSettings.setOsuId(userData.getId());
                userSettings.setOsuName(userData.getUsername());
                userSettings.setQqId(qq);
                userSettings.setDefaultMode(userData.playmode);
                userSettings.setVerificationCode("MANUAL");
                userSettings.setVerificationState(VerificationStates.VERIFIED);
                userSettings.setVerificationTime(System.currentTimeMillis());


                return "Success? " + userSettings.saveOrUpdate();
            }
            catch (UserNotFoundException e)
            {
                return "未找到. " + e.toString();
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("setid--force");
    }
}
