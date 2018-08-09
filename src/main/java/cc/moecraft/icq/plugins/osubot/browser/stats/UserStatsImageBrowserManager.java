package cc.moecraft.icq.plugins.osubot.browser.stats;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class UserStatsImageBrowserManager
{
    private ArrayList<UserStatsImageBrowser> browsers = new ArrayList<>();

    public UserStatsImageBrowser getBrowser()
    {
        for (UserStatsImageBrowser browser : browsers)
        {
            if (!browser.isRunning()) return browser.setRunning(true);
        }

        return createBrowser();
    }

    private UserStatsImageBrowser createBrowser()
    {
        UserStatsImageBrowser browser = new UserStatsImageBrowser();
        browsers.add(browser);
        return browser;
    }
}
