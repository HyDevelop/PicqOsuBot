package cc.moecraft.icq.plugins.osubot.utils;

import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
public class BrowserUtils
{
    public static File getScreenshot(BrowserView view, String fileName,
                                     int startX, int startY, int endX, int endY) throws IOException
    {
        // 截图
        LightWeightWidget lightWeightWidget = (LightWeightWidget) view.getComponent(0);

        Image baseImage = lightWeightWidget.getImage();
        BufferedImage baseBufferedImage = ImageUtils.toBufferedImage(baseImage);

        // 裁剪
        baseBufferedImage = ImageUtils.cropImage(baseBufferedImage, startX, startY, endX, endY);

        // 缓存到文件
        File file = new File(ResourceFileUtils.getCacheDir(), fileName.replace("%{ms}", String.valueOf(System.currentTimeMillis())));
        file.getParentFile().mkdirs();
        ImageIO.write(baseBufferedImage, "PNG", file);

        return file;
    }
}
