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
