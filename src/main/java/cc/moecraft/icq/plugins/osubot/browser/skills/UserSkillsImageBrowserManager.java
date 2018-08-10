package cc.moecraft.icq.plugins.osubot.browser.skills;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class UserSkillsImageBrowserManager
{
    private ArrayList<UserSkillsImageBrowser> browsers = new ArrayList<>();

    public UserSkillsImageBrowser getBrowser()
    {
        for (UserSkillsImageBrowser browser : browsers)
        {
            if (!browser.isRunning()) return browser.setRunning(true);
        }

        return createBrowser();
    }

    private UserSkillsImageBrowser createBrowser()
    {
        UserSkillsImageBrowser browser = new UserSkillsImageBrowser();
        browsers.add(browser);
        return browser;
    }
}
