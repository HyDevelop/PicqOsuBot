package cc.moecraft.icq.plugins.osubot.browser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class OsuBrowserManager
{
    private Map<Class<? extends OsuBrowser>, ArrayList<OsuBrowser>> browsers = new HashMap<>();

    private <T extends OsuBrowser> T createBrowser(Class<T> browserClass)
    {
        try
        {
            T browser = browserClass.newInstance();
            browsers.get(browserClass).add(browser);
            return browser;
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
