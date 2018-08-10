package cc.moecraft.icq.plugins.osubot.browser.recent;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class RecentImageBrowserManager
{
    private ArrayList<RecentImageBrowser> browsers = new ArrayList<>();

    public RecentImageBrowser getBrowser()
    {
        for (RecentImageBrowser browser : browsers)
        {
            if (!browser.isRunning()) return browser.setRunning(true);
        }

        return createBrowser();
    }

    private RecentImageBrowser createBrowser()
    {
        RecentImageBrowser browser = new RecentImageBrowser();
        browsers.add(browser);
        return browser;
    }
}
