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

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     *
     * @author @Sri Harsha Chilakapati
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage) return (BufferedImage) img;

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }
}
