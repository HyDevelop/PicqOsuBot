package cc.moecraft.icq.plugins.osubot.osu.webapi;

import cc.moecraft.icq.plugins.osubot.osu.exceptions.JsonNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class OWAUtils
{
    public static final Pattern patternToFindUserId = Pattern.compile("(?<=<meta http-equiv=\"refresh\" content=\"0;url=https://osu.ppy.sh/users/).*(?=\" />)");

    /**
     * 获取用户页面HTML
     * @param username 用户名
     * @return HTML字符串
     * @throws UserNotFoundException 用户未找到
     */
    public static String getUserPageHtml(String username) throws UserNotFoundException
    {
        username = username.replace(" ", "%20");
        String redirectHtml = getUserPageHtmlRaw(username);
        Matcher matcher = patternToFindUserId.matcher(redirectHtml);
        if (matcher.find()) return getUserPageHtml(Long.parseLong(matcher.group()));
        else throw new UserNotFoundException(username, redirectHtml);
    }

    /**
     * 获取用户界面HTML
     * @param userId 用户ID
     * @return HTML字符串
     */
    public static String getUserPageHtml(long userId)
    {
        return getUserPageHtmlRaw(String.valueOf(userId));
    }

    private static String getUserPageHtmlRaw(String rawUserIndicator)
    {
        return HttpUtil.downloadString("https://osu.ppy.sh/users/" + rawUserIndicator, "utf-8");
    }

    /**
     * 从HTML中提取Json块
     * @param html HTML
     * @param jsonName Json名
     * @return 提取出来的Json对象
     * @throws JsonNotFoundException 未找到
     */
    public static JsonElement grepJson(String html, String jsonName) throws JsonNotFoundException
    {
        Pattern pattern = Pattern.compile("(?<=<script id=\"json-" + jsonName + "\" type=\"application/json\">)(.*?)(?=</script>)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find())
            return new JsonParser().parse(matcher.group());
        else throw new JsonNotFoundException(html, jsonName, pattern, matcher);
    }

    /**
     * 获取用户数据
     * @param username 用户名
     * @return 用户数据
     * @throws UserNotFoundException 用户未找到
     */
    public static OWAUserData getUserData(String username) throws UserNotFoundException
    {
        try
        {
            return new Gson().fromJson(grepJson(getUserPageHtml(username), "user"), OWAUserData.class);
        }
        catch (JsonNotFoundException e)
        {
            throw new UserNotFoundException(username, e.html);
        }
    }
}
