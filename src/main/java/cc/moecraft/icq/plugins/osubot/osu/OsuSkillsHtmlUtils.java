package cc.moecraft.icq.plugins.osubot.osu;

import cc.moecraft.icq.plugins.osubot.browser.browsers.SkillsBrowser;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.utils.ResourceFileUtils;
import cc.moecraft.utils.StringUtils;
import cn.hutool.http.HttpUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class OsuSkillsHtmlUtils
{
    private static Pattern pattern = Pattern.compile("(?<=<output class=\"skillValue\">)[0-9]*?(?=</output>)");

    public static int[] getSkillLevels(String playerName) throws UserNotFoundException
    {
        playerName = playerName.replace(" ", "%20");

        String html = HttpUtil.downloadString("http://osuskills.com/user/" + playerName, "utf-8");
        Matcher matcher = pattern.matcher(html);

        int[] result = new int[6];

        for (int i = 0; i < 6; i++)
        {
            if (!matcher.find()) throw new UserNotFoundException(playerName, html);
            result[i] = Integer.parseInt(matcher.group());
        }

        return result;
    }

    public static int[] getPercentage(int[] skills)
    {
        // 获取最大
        double max = skills[0];
        for (int skill : skills) max = Math.max(skill, max);

        max /= 0.8d;

        // 通过最大获取百分比
        int[] result = new int[6];
        for (int i = 0; i < skills.length; i++) result[i] = (int) Math.round((double) skills[i] / (double) max * 100d);

        return result;
    }

    public static String getSkillJsPatch(String username) throws UserNotFoundException
    {
        int[] skills = OsuSkillsHtmlUtils.getSkillLevels(username);
        int[] skillsPercent = OsuSkillsHtmlUtils.getPercentage(skills);

        return StringUtils.replaceVariables(ResourceFileUtils.readResource(SkillsBrowser.class, "js/user-skills-edits.js"), "temp", "temp",
                "1", String.valueOf(skills[0]),
                "2", String.valueOf(skills[1]),
                "3", String.valueOf(skills[2]),
                "4", String.valueOf(skills[3]),
                "5", String.valueOf(skills[4]),
                "6", String.valueOf(skills[5]),
                "1p", String.valueOf(skillsPercent[0]),
                "2p", String.valueOf(skillsPercent[1]),
                "3p", String.valueOf(skillsPercent[2]),
                "4p", String.valueOf(skillsPercent[3]),
                "5p", String.valueOf(skillsPercent[4]),
                "6p", String.valueOf(skillsPercent[5]),
                "1star", skillsPercent[0] == 80 ? " bar--beatmap-stats-stars" : "",
                "2star", skillsPercent[1] == 80 ? " bar--beatmap-stats-stars" : "",
                "3star", skillsPercent[2] == 80 ? " bar--beatmap-stats-stars" : "",
                "4star", skillsPercent[3] == 80 ? " bar--beatmap-stats-stars" : "",
                "5star", skillsPercent[4] == 80 ? " bar--beatmap-stats-stars" : "",
                "6star", skillsPercent[5] == 80 ? " bar--beatmap-stats-stars" : "");
    }
}
