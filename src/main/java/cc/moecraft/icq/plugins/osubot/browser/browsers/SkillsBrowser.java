package cc.moecraft.icq.plugins.osubot.browser.browsers;

import cc.moecraft.icq.plugins.osubot.browser.OsuBrowser;
import cc.moecraft.icq.plugins.osubot.osu.OsuSkillsHtmlUtils;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;

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
public class SkillsBrowser extends OsuBrowser
{
    public SkillsBrowser()
    {
        super(400, 818);
    }

    public File render(String username) throws UserNotFoundException, IOException
    {
        String js = OsuSkillsHtmlUtils.getSkillJsPatch(username);
        load("https://osu.ppy.sh/users/" + username + "/osu");
        sleep(500);
        execute(js);
        sleep(1500);
        setRunning(false);
        return render(0, 77, 382, 636 - 150);
    }

    @Override
    public String name()
    {
        return "skills";
    }
}
