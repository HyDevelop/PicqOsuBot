package cc.moecraft.icq.plugins.osubot.browser.skills;

import cc.moecraft.icq.plugins.osubot.osu.OsuSkillsHtmlUtils;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
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
import java.io.IOException;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class UserSkillsImageBrowser
{
    private final Browser userSkillsBrowser;
    private final BrowserView userSkillsView;

    @Getter
    private boolean running = false;

    public UserSkillsImageBrowser()
    {
        userSkillsBrowser = new Browser(BrowserType.LIGHTWEIGHT);
        userSkillsView = new BrowserView(userSkillsBrowser);
        userSkillsBrowser.setSize(400, 818);
    }

    public File getUserSkillsImage(String username) throws UserNotFoundException
    {
        try
        {
            String js = OsuSkillsHtmlUtils.getSkillJsPatch(username);
            Browser.invokeAndWaitFinishLoadingMainFrame(userSkillsBrowser, browser1 -> browser1.loadURL("https://osu.ppy.sh/users/" + username + "/osu"));

            Thread.sleep(500);

            userSkillsBrowser.executeJavaScript(js);

            Thread.sleep(1500);

            LightWeightWidget lightWeightWidget = (LightWeightWidget) userSkillsView.getComponent(0);
            Image baseImage = lightWeightWidget.getImage();
            BufferedImage baseBufferedImage = ImageUtils.toBufferedImage(baseImage);

            // 裁剪
            baseBufferedImage = ImageUtils.cropImage(baseBufferedImage, 0, 77, 382, 636 - 150);

            // 缓存到文件
            File file = new File("./cache/image/skills/skill-cr-" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            ImageIO.write(baseBufferedImage, "PNG", file);

            return file;
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            running = false;
        }
    }

    public UserSkillsImageBrowser setRunning(boolean running)
    {
        this.running = running;
        return this;
    }
}
