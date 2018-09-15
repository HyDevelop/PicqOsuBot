package ignored;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.plugins.osubot.commands.setid.VerificationStates;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.icq.plugins.osubot.utils.JbootUtils;
import cc.moecraft.icq.plugins.permissions.utils.CommandUtils;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStrangerInfo;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.utils.ArrayUtils;
import cc.moecraft.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static cc.moecraft.logger.format.AnsiColor.*;
import static java.lang.System.in;

/**
 * 此类由 Hykilpikonna 在 2018/09/04 创建!
 * Created by Hykilpikonna on 2018/09/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ImportBind
{
    public static void main(String[] args)
    {
        File dir = new File("./cache/osu/");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PicqBotX bot = new PicqBotX("127.0.0.1", 31090, 31092, false);
        JbootUtils.initializeJFinalSafe();
        JbootUtils.disableSqlReport();
        JbootUtils.setDevMode(false);
        IcqHttpApi api = bot.getAccountManager().getNonAccountSpecifiedApi();

        for (File file : dir.listFiles())
        {
            try
            {
                Long qq = Long.valueOf(file.getName());
                String username = FileUtils.readFileAsString(file);
                OWAUserData userData = OWAUtils.getUserData(username);
                RStrangerInfo qqData = api.getStrangerInfo(qq).data;

                System.out.println("QQ用户: " + YELLOW + qqData.getNickname() + " " + PURPLE + qqData.getSex() + BLUE + " " + qqData.getUserId() + RESET);
                System.out.println("Osu用户: " + YELLOW + userData.getUsername() + " " + RED
                        + userData.getCountry().getName() + " " +
                        GREEN + userData.getStatistics().getPp() + RESET);

                String approve = "";
                while (!approve.equals("y") && !approve.equals("n"))
                {
                    System.out.println("Approve? (y/n)");
                    approve = reader.readLine();
                }

                if (!approve.equals("y")) continue;

                HoUserSettings userSettings = new HoUserSettings();
                userSettings.setOsuId(userData.getId());
                userSettings.setOsuName(userData.getUsername());
                userSettings.setQqId(qq);
                userSettings.setDefaultMode(userData.playmode);
                userSettings.setVerificationCode("FROM KJ");
                userSettings.setVerificationState(VerificationStates.VERIFIED);
                userSettings.setVerificationTime(System.currentTimeMillis());

                System.out.println("Success? " + userSettings.saveOrUpdate());
            }
            catch (UserNotFoundException e)
            {
                System.out.println("未找到: " + e.getRequestedUserIndicator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
