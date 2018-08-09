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

    public File getUserStatsImage(long userId)
    {
        try
        {
            // #3 Load web page and wait until web page is loaded completely
            Browser.invokeAndWaitFinishLoadingMainFrame(userStatsBrowser, browser1 -> browser1.loadURL("https://osu.ppy.sh/users/" + userId));

            Thread.sleep(1500);

            userStatsBrowser.executeJavaScript(userStatsScript);

            Thread.sleep(1500);

            // #4 Get java.awt.Image of the loaded web page.
            LightWeightWidget lightWeightWidget = (LightWeightWidget) userStatsView.getComponent(0);

            Image baseImage = lightWeightWidget.getImage();
            BufferedImage baseBufferedImage = ImageUtils.toBufferedImage(baseImage);

            // 裁剪
            baseBufferedImage = ImageUtils.cropImage(baseBufferedImage, 0, 77, 382, 636);

            // 缓存到文件
            File file = new File("./cache/image/stats/s-cr-" + userId + "-" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            ImageIO.write(baseBufferedImage, "PNG", file);

            // Dispose Browser instance
            // userStatsBrowser.dispose();

            return file;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            running = false;
        }
    }

    public UserStatsImageBrowser setRunning(boolean running)
    {
        this.running = running;
        return this;
    }
}
