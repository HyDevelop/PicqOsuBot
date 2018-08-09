package cc.moecraft.icq.plugins.osubot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiaoleilu.hutool.http.HttpUtil;

import java.net.MalformedURLException;
import java.nio.charset.Charset;

/**
 * 此类由 Hykilpikonna 在 2018/08/02 创建!
 * Created by Hykilpikonna on 2018/08/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class DownloadUtils
{
    public static String DEFAULT_CHARSET = "utf-8";

    /**
     * 从URL获取JSON对象
     *
     * @param url URL
     * @return JSON对象
     * @exception MalformedURLException URL解析失败
     */
    public static JsonElement getJsonElementFromURL(String url, String charset) throws MalformedURLException
    {
        return new JsonParser().parse(downloadAsString(url, charset));
    }

    /**
     * 下载HTTP数据为字符串
     *
     * @param url 下载地址
     * @return 下载到的字符串
     */
    public static String downloadAsString(String url, String charset)
    {
        return HttpUtil.downloadString(url, Charset.forName(charset));
    }
}
