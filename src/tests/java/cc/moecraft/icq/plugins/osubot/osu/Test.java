package cc.moecraft.icq.plugins.osubot.osu;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class Test
{
    public static void main(String[] args)
    {
        System.out.println(getCurrentSixDigitCode());
    }

    public static int getCurrentSixDigitCode()
    {
        return getCurrentSixDigitCode(System.currentTimeMillis());
    }

    public static int getCurrentSixDigitCode(long timestamp)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);

        return (int) ((getRandom(1, 9 - (day / 10)) * 10 + day) * 10000 + hour * 1000 + (timestamp / 100 % 1000));
    }

    public static int getRandom(int start, int end)
    {
        return new Random().nextInt(end) + start;
    }

    public static void test()
    {
        for (int i = 0; i < 60; i++)
        {
            for (int j = 60; j < 60; j++)
            {
                System.out.println(String.format("%s分, %s秒: %s", i, j, (((i * 60) + j) * 1000)));
            }
        }
    }
}
