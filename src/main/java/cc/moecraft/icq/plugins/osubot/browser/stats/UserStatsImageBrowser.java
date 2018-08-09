package cc.moecraft.icq.plugins.osubot.browser.stats;

import cc.moecraft.icq.plugins.osubot.utils.ImageUtils;
import cc.moecraft.icq.plugins.osubot.utils.ResourceFileUtils;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class UserStatsImageBrowser
{
    private final Browser userStatsBrowser;
    private final BrowserView userStatsView;
    private static String userStatsScript;

    @Getter
    private boolean running = false;

    static
    {
        userStatsScript = ResourceFileUtils.readResource(UserStatsImageBrowser.class, "js/user-stats-edits.js");
    }

    public UserStatsImageBrowser()
    {
        // #1 Create Browser instance
        userStatsBrowser = new Browser(BrowserType.LIGHTWEIGHT);

        // userStatsBrowser.getContext().getNetworkService().setNetworkDelegate(new MobileBrowserUADelegate());
        userStatsView = new BrowserView(userStatsBrowser);

        // #2 Set the required view size
        userStatsBrowser.setSize(400, 818);
    }
}
