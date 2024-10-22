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

    /**
     * 加载html
     * @param html HTML字符串
     */
    public void loadHtml(String html)
    {
        load(cacheHtml(html));
    }

    /**
     * 加载html
     * @param resourceName 资源名
     * @param variables 变量对象
     */
    public void loadHtml(String resourceName, Object ... variables)
    {
        load(cacheHtml(resourceName, variables));
    }

    /**
     * 名字
     * @return 名字
     */
    public abstract String name();

    /**
     * 设置是否在运行
     * @param running 是否在运行
     * @return 这个实例
     */
    public OsuBrowser setRunning(boolean running)
    {
        this.running = running;
        return this;
    }

    /**
     * 睡
     * @param millis 时长 (毫秒
     */
    public void sleep(long millis)
    {
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 从资源执行js
     * @param resource 资源
     */
    public void executeFromResource(String resource)
    {
        execute(ResourceFileUtils.readResource(getClass(), resource));
    }

    /**
     * 直接执行js
     * @param js js字符串
     */
    public void execute(String js)
    {
        browser.executeJavaScript(js);
    }

    /**
     * 截图
     * @param startX 起点的X
     * @param startY 起点的Y
     * @param endX 终点的X
     * @param endY 终点的Y
     * @return 截图文件
     * @throws IOException 文件保存错误
     */
    public File render(int startX, int startY, int endX, int endY) throws IOException
    {
        return BrowserUtils.getScreenshot(view, "image/" + name() + "/img-%{ms}.png", startX, startY, endX, endY);
    }

    /**
     * 截图
     * @param startX 起点的X
     * @param startY 起点的Y
     * @param endX 终点的X
     * @param endY 终点的Y
     * @param scale 放大比例
     * @return 截图文件
     * @throws IOException 文件保存错误
     */
    public File render(int startX, int startY, int endX, int endY, float scale) throws IOException
    {
        return render(Math.round(startX * scale), Math.round(startY * scale), Math.round(endX * scale), Math.round(endY * scale));
    }

    /**
     * 从资源获取HTML
     * @param resourceName 资源名
     * @param variables 变量对象
     * @return HTML字符串
     */
    public String getHtml(String resourceName, Object ... variables)
    {
        String html = ResourceFileUtils.readResource(getClass(), resourceName);
        for (Object variable : variables) ReflectUtils.replaceReflectVariables(variable, html, false, true);
        return html;
    }

    /**
     * 从资源缓存HTML文件
     * @param resourceName 资源名
     * @param variables 变量对象
     * @return HTML文件
     */
    public File cacheHtml(String resourceName, Object ... variables)
    {
        return cacheHtml(getHtml(resourceName, variables));
    }

    /**
     * 缓存HTML文件
     * @param html html字符串
     * @return html文件
     */
    public File cacheHtml(String html)
    {
        File htmlFile = new File(ResourceFileUtils.getCacheDir(), "web/" + name() + "/html-%{ms}.html"
                .replace("%{ms}", String.valueOf(System.currentTimeMillis())));
        try
        {
            FileUtils.writeStringToFile(htmlFile, html, "utf-8");
            return htmlFile;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
