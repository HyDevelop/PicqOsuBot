package cc.moecraft.icq.plugins.osubot.browser.browsers;

import cc.moecraft.icq.plugins.osubot.browser.OsuBrowser;

import java.io.File;
import java.io.IOException;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class StatsBrowser extends OsuBrowser
{
    public StatsBrowser()
    {
        super(400, 818);
    }

    public File render(long userId) throws IOException
    {
        load("https://osu.ppy.sh/users/" + userId);
        sleep(1500);
        executeFromResource("js/user-stats-edits.js");
        sleep(1500);
        setRunning(false);
        return render(0, 77, 382, 636);
    }

    @Override
    public String name()
    {
        return "stats";
    }
}
