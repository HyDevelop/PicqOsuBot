package cc.moecraft.icq.plugins.osubot.utils;

import java.util.Random;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class MathUtils
{
    /**
     * 四舍五入
     * @param original 源
     * @param decimals 小数点后几位 ( 0: 1 | 1: 0.1 | 4: 0.0001 )
     * @return 四舍五入之后的
     */
    public static double round(double original, int decimals)
    {
        double scale = Math.pow(10d, decimals);
        return Math.round(original * scale) / scale;
    }

    public static int getRandom(int start, int end)
    {
        return new Random().nextInt(end) + start;
    }
}
