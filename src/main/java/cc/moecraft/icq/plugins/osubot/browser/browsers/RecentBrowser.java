package cc.moecraft.icq.plugins.osubot.browser.browsers;

import cc.moecraft.icq.plugins.osubot.browser.OsuBrowser;
import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.Mods;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.*;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.OPAWrapper;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPABeatmapData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserRecentData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.icq.plugins.osubot.utils.KoohiiUtils;
import cc.moecraft.utils.MathUtils;
import cc.moecraft.utils.ReflectUtils;
import cc.moecraft.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class RecentBrowser extends OsuBrowser
{
    public RecentBrowser()
    {
        super(1180, 160);
    }

    public File render(String username) throws UserNotFoundException, RecentScoreNotEnoughException, IOException, UnsupportedModeException
    {
        try
        {
            OWAUserData userData = OWAUtils.getUserData(username);
            OPAUserRecentData recentData = OPAWrapper.getRecent(username, 1, Mode.parse(userData.getPlaymode()).getCode());
            OPABeatmapData beatmapData = OPAWrapper.getBeatmap(recentData);

            return render(beatmapData, userData, recentData);
        }
        catch (JsonEmptyException e)
        {
            throw new UserNotFoundException(username, e.getMessage());
        }
        catch (ModeNotFoundException | IllegalAccessException e)
        {
            throw new RuntimeException(e); // 不会发生
        }
    }

    public File render(OPABeatmapData beatmapData, OWAUserData userData, OPAUserRecentData recentData)
            throws IOException, IllegalAccessException, UnsupportedModeException
    {
        Mode mode = Mode.parse(beatmapData.getMode());
        Mods mods = new Mods(recentData.enabledMods);
        KoohiiUtils.PpData ppData = KoohiiUtils.calculate(mode, recentData, 1);
        ReflectUtils.roundAllNumbers(beatmapData, 2);
        String html = StringUtils.replaceVariables(getHtml("web/scores/STDTemplate.html"), "temp", "temp",
                "avatar-url", userData.getAvatarUrl(),
                "username", userData.getUsername(),
                "rel-time", recentData.getDate(),
                "score-short", recentData.getRank(),
                "bm-title", StringUtils.limitSize(beatmapData.getTitle(), 20),
                "bm-artist", StringUtils.limitSize(beatmapData.getArtist(), 12),
                "bm-version", beatmapData.getVersion(),
                "cs", beatmapData.getDiffSize(),
                "hp", beatmapData.getDiffDrain(),
                "od", beatmapData.getDiffOverall(),
                "ar", beatmapData.getDiffApproach(),
                "stars", beatmapData.getDifficultyRating(),
                "stars.aim", MathUtils.round(ppData.getDiffCalc().aim, 2),
                "stars.speed", MathUtils.round(ppData.getDiffCalc().speed, 2),
                "score", StringUtils.toNumberWithComma(recentData.getScore()),
                "acc", MathUtils.round(recentData.getAcc(beatmapData.getMode()) * 100d, 1),
                "combo", StringUtils.toNumberWithComma(recentData.getMaxCombo()),
                "h300", StringUtils.toNumberWithComma(recentData.getCount300()),
                "h100", StringUtils.toNumberWithComma(recentData.getCount100()),
                "h50", StringUtils.toNumberWithComma(recentData.getCount50()),
                "h0", StringUtils.toNumberWithComma(recentData.getCountMiss()),
                "pp", MathUtils.round(ppData.getPp().total, 2),
                "ppa", MathUtils.round(ppData.getPp().aim, 2),
                "pps", MathUtils.round(ppData.getPp().speed, 2),
                "mods", mods.toHtml()
        );
        loadHtml(html);
        sleep(1000);
        setRunning(false);
        return render(0, 0, 1142, 142); //TODO: change dis
    }

    @Override
    public String name()
    {
        return "recent";
    }
}
