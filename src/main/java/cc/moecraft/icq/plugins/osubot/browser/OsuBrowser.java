package cc.moecraft.icq.plugins.osubot.browser;

import cc.moecraft.icq.plugins.osubot.utils.BrowserUtils;
import cc.moecraft.icq.plugins.osubot.utils.ResourceFileUtils;
import cc.moecraft.utils.ReflectUtils;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public abstract class OsuBrowser
{
    @Getter
    private boolean running = false;
    protected final Browser browser;
    protected final BrowserView view;

    public OsuBrowser(int width, int height)
    {
        // 创建实例
        browser = new Browser(BrowserType.LIGHTWEIGHT);
        view = new BrowserView(browser);

        // 设置分辨率
        browser.setSize(width, height);
    }

    /**
     * 加载URL
     * @param url URL
     */
    public void load(String url)
    {
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, browser1 -> browser1.loadURL(url));
    }

    /**
     * 加载本地文件
     * @param file 本地文件
     */
    public void load(File file)
    {
        load(file.getAbsolutePath());
    }
}
