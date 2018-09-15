package cc.moecraft.icq.plugins.osubot.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ImageUtils
{
    /**
     * 裁剪图片方法
     * @param original 图像源
     * @param startX 裁剪开始x坐标
     * @param startY 裁剪开始y坐标
     * @param endX 裁剪结束x坐标
     * @param endY 裁剪结束y坐标
     * @return 处理后的
     *
     * @author @FrightingForAmbition
     */
    public static BufferedImage cropImage(BufferedImage original, int startX, int startY, int endX, int endY)
    {
        int width = original.getWidth();
        int height = original.getHeight();

        if (startX == -1) startX = 0;
        if (startY == -1) startY = 0;
        if (endX == -1) endX = width - 1;
        if (endY == -1) endY = height - 1;

        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);

        for (int x = startX; x < endX; ++x)
        {
            for (int y = startY; y < endY; ++y)
            {
                int rgb = original.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }

        return result;
    }
}
