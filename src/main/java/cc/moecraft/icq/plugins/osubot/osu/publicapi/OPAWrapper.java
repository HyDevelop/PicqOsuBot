package cc.moecraft.icq.plugins.osubot.osu.publicapi;

import cc.moecraft.icq.plugins.osubot.osu.exceptions.BeatmapScoreNotEnoughException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.JsonEmptyException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.RecentScoreNotEnoughException;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPABeatmapData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserBestData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserRecentData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserScoreData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPABeatmapParams;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPAUserBestParams;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPAUserRecentParams;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPAUserScoreParams;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/04 创建!
 * Created by Hykilpikonna on 2018/05/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * OsuAPIUtils 的封装
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class OPAWrapper
{

    // 谱面

    /**
     * 获取谱面组
     * 
     * @param parameters 谱面参数
     * @return 谱面组
     */
    public static ArrayList<OPABeatmapData> getBeatmap(OPABeatmapParams parameters) throws JsonEmptyException
    {
        ArrayList<OPABeatmapData> beatmapDataArrayList = new ArrayList<>();
        OPAUtils.get(parameters).forEach(data -> beatmapDataArrayList.add((OPABeatmapData) data));
        return beatmapDataArrayList;
    }

    /**
     * 获取谱面
     * 
     * @param recent 谱面参数
     * @return 谱面
     */
    public static OPABeatmapData getBeatmap(OPAUserRecentData recent) throws JsonEmptyException
    {
        return getBeatmap(OPABeatmapParams.builder().b(String.valueOf(recent.getBeatmapId())).build()).get(0);
    }

    // recent成绩

    /**
     * 获取recent成绩组
     *
     * @param parameters recent参数
     * @return 成绩组
     */
    public static ArrayList<OPAUserRecentData> getRecent(OPAUserRecentParams parameters) throws JsonEmptyException
    {
        ArrayList<OPAUserRecentData> beatmapDataArrayList = new ArrayList<>();
        OPAUtils.get(parameters).forEach(data -> beatmapDataArrayList.add((OPAUserRecentData) data));
        return beatmapDataArrayList;
    }

    /**
     * 获取recent成绩
     *
     * @param username 用户名
     * @param index 第几项 (从1开始
     * @return recent成绩
     */
    public static OPAUserRecentData getRecent(String username, int index, int mode) throws JsonEmptyException, RecentScoreNotEnoughException
    {
        ArrayList<OPAUserRecentData> recents = getRecent(OPAUserRecentParams.builder()
                .u(username)
                .limit(String.valueOf(index))
                .type("string")
                .m(String.valueOf(mode))
                .build());

        if (recents.size() < index) throw new RecentScoreNotEnoughException(recents.size(), index, mode);

        return recents.get(index - 1);
    }
    
    // score成绩

    /**
     * 获取Scores成绩组
     *
     * @param parameters scores参数
     * @return scores成绩组
     */
    public static ArrayList<OPAUserScoreData> getScore(OPAUserScoreParams parameters, boolean sort) throws JsonEmptyException
    {
        ArrayList<OPAUserScoreData> obtainedData = new ArrayList<>();
        OPAUtils.get(parameters).forEach(data -> obtainedData.add((OPAUserScoreData) data));
        if (sort) obtainedData.sort(OPAUserScoreData::compareTo);

        return obtainedData;
    }

    /**
     * 获取一个谱面的最佳成绩
     *
     * @param data 谱面
     * @return 最佳成绩
     */
    public static OPAUserScoreData getScore(OPABeatmapData data) throws JsonEmptyException, BeatmapScoreNotEnoughException
    {
        ArrayList<OPAUserScoreData> obtainedData = getScore(OPAUserScoreParams.builder().b(String.valueOf(data.getBeatmapId())).m(String.valueOf(data.getMode())).mods("0").limit("2").build(), false);

        if (obtainedData.size() < 1) throw new BeatmapScoreNotEnoughException();

        return obtainedData.get(0);
    }

    // best成绩

    /**
     * 获取Best成绩组
     *
     * @param parameters Best参数
     * @return Best成绩组
     */
    public static ArrayList<OPAUserBestData> getUserBest(OPAUserBestParams parameters) throws JsonEmptyException
    {
        ArrayList<OPAUserBestData> beatmapDataArrayList = new ArrayList<>();
        OPAUtils.get(parameters).forEach(data -> beatmapDataArrayList.add((OPAUserBestData) data));
        beatmapDataArrayList.sort(OPAUserBestData::compareTo);

        return beatmapDataArrayList;
    }

    /**
     * 计算精准度
     * @param mode 模式
     * @param count300 300
     * @param count100 100
     * @param count50 50
     * @param countMiss Miss
     * @param countKatu 喝
     * @param countgeki 激
     * @return ACC
     */
    public static double getAcc(int mode, int count300, int count100, int count50, int countMiss, int countKatu, int countgeki)
    {
        double acc = 0;
        switch (mode)
        {
            case 0:
                acc = (count300 * 300.0 + count100 * 100.0 + count50 * 50.0) / ((count300 + count100 + count50 + countMiss) * 300);
                break;
            case 1:
                acc = (count300 + count100 * 0.5) / (count300 + count100 + countMiss);
                break;
            case 2:
                int base = count300 + count100 + count50;
                acc = (double) base / (base + (countMiss + countKatu));
                break;
            case 3:
                acc = (double)(count50 * 50 + count100 * 100 + countKatu * 200 + (count300 + countgeki) * 300) / ((count300 + count100 + count50 + countMiss + countKatu + countgeki) * 300);
                break;
            default:
                break;
        }

        return acc;
    }
}
