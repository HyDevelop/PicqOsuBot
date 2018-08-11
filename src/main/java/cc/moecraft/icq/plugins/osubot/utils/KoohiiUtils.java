package cc.moecraft.icq.plugins.osubot.utils;

import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.Mods;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserRecentData;
import cn.hutool.http.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static com.github.francesco149.koohii.Koohii.*;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class KoohiiUtils
{
    public static PpData calculate(Mode mode, OPAUserRecentData recentData, int scoreVersion)
    {
        return calculate(recentData.beatmapId, mode, new Mods(recentData.enabledMods),
                recentData.maxCombo, recentData.getCount300Total(), recentData.getCount100Total(), recentData.count50, recentData.countMiss,
                scoreVersion);
    }

    public static PpData calculate(long mapBid, Mode mode, Mods mods,
                                   int combo, int n300, int n100, int n50, int nmiss,
                                   int score_version)
    {
        Map map = getMap(mapBid, mode);
        assert map != null;

        DiffCalc stars = new DiffCalc().calc(map);

        PPv2Parameters parameters = new PPv2Parameters();
        parameters.beatmap = map;
        parameters.aim_stars = stars.aim;
        parameters.speed_stars = stars.speed;
        parameters.mode = mode.getCode();
        parameters.mods = (int) mods.toDec();
        parameters.combo = combo;
        parameters.n300 = n300;
        parameters.n100 = n100;
        parameters.n50 = n50;
        parameters.nmiss = nmiss;
        parameters.score_version = score_version;

        PPv2 pp = new PPv2(parameters);

        return new PpData(stars, pp, map);
    }

    @Data @AllArgsConstructor
    public static class PpData
    {
        private DiffCalc diffCalc;
        private PPv2 pp;
        private Map map;
    }

    public static Map getMap(long bid, Mode mode)
    {
        try
        {
            return new Parser().map(new BufferedReader(
                    new StringReader(HttpUtil.downloadString("https://osu.ppy.sh/osu/" + bid + "&m=" + mode.getCode(), "utf-8"))));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
