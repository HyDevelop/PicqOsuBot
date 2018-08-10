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
        // 创建实例
        userStatsBrowser = new Browser(BrowserType.LIGHTWEIGHT);
        userStatsView = new BrowserView(userStatsBrowser);

        // 设置分辨率
        userStatsBrowser.setSize(400, 818);
    }

    public File getUserStatsImage(long userId)
    {
        try
        {
            // 加载网页
            Browser.invokeAndWaitFinishLoadingMainFrame(userStatsBrowser, browser1 -> browser1.loadURL("https://osu.ppy.sh/users/" + userId));

            Thread.sleep(1500);

            userStatsBrowser.executeJavaScript(userStatsScript);

            Thread.sleep(1500);

            // 截图
            LightWeightWidget lightWeightWidget = (LightWeightWidget) userStatsView.getComponent(0);

            Image baseImage = lightWeightWidget.getImage();
            BufferedImage baseBufferedImage = ImageUtils.toBufferedImage(baseImage);

            // 裁剪
            baseBufferedImage = ImageUtils.cropImage(baseBufferedImage, 0, 77, 382, 636);

            // 缓存到文件
            File file = new File("./cache/image/stats/s-cr-" + userId + "-" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            ImageIO.write(baseBufferedImage, "PNG", file);

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
