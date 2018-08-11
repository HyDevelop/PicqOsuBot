package cc.moecraft.icq.plugins.osubot.utils;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class URLBuilder
{
    private StringBuilder url;

    public URLBuilder(String baseUrl)
    {
        url = new StringBuilder(baseUrl);
    }

    /**
     * 添加HTTP请求参数
     * @param key 键
     * @param value 值
     */
    public URLBuilder addParameter(String key, String value)
    {
        if (!url.toString().endsWith("&") || !url.toString().endsWith("?")) url.append("&");
        url.append(key).append("=").append(value);
        return this;
    }

    @Override
    public String toString()
    {
        return url.toString();
    }
}
