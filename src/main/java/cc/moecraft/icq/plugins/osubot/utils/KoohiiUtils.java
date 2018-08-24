package cc.moecraft.icq.plugins.osubot.utils;

import cc.moecraft.icq.plugins.osubot.osu.Koohii;
import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.Mods;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UnsupportedModeException;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserRecentData;
import cn.hutool.http.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import static cc.moecraft.icq.plugins.osubot.osu.Koohii.*;


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
    public static PpData calculate(Mode mode, OPAUserRecentData recentData, int scoreVersion) throws UnsupportedModeException
    {
        return calculate(recentData.beatmapId, mode, new Mods(recentData.enabledMods),
                recentData.maxCombo, recentData.getCount300(), recentData.getCount100(), recentData.count50, recentData.countMiss,
                scoreVersion);
    }

    public static PpData calculate(long mapBid, Mode mode, Mods mods,
                                   int combo, int n300, int n100, int n50, int nmiss,
                                   int score_version) throws UnsupportedModeException
    {
        Map map = getMap(mapBid, mode);
        assert map != null;

        DiffCalc stars = new DiffCalc().calc(map, (int) mods.toDec());

        PPv2Parameters parameters = new PPv2Parameters();
        // parameters.beatmap = map;
        parameters.mode = map.mode;
        parameters.base_ar = map.ar;
        parameters.base_od = map.od;
        parameters.max_combo = map.max_combo();
        parameters.nsliders = map.nsliders;
        parameters.ncircles = map.ncircles;
        parameters.nobjects = map.objects.size();
        parameters.aim_stars = stars.aim;
        parameters.speed_stars = stars.speed;
        parameters.mode = mode.getCode();
        parameters.mods = (int) mods.toDec();
        parameters.combo = combo;
        parameters.nobjects = map.objects.size();
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

    public static Map getMap(long bid, Mode mode) throws UnsupportedModeException
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
        catch (UnsupportedOperationException e)
        {
            throw new UnsupportedModeException(mode);
        }
    }
}
