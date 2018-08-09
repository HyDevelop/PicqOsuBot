package cc.moecraft.icq.plugins.osubot.utils;

import cc.moecraft.icq.plugins.osubot.Main;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 此类由 Hykilpikonna 在 2018/08/09 创建!
 * Created by Hykilpikonna on 2018/08/09!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class FileCacheManager
{
    public static File getCachedResourceFile(String path, String resource)
    {
        try
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR);

            File helpFile = new File(path
                    .replace("%{d}", String.valueOf(day))
                    .replace("%{h}", String.valueOf(hour)));
            helpFile.getParentFile().mkdirs();

            if (helpFile.exists()) return helpFile;
            else ResourceFileUtils.copyResource(Main.class, resource, helpFile);

            return helpFile;
        }
        catch (IOException e)
        {
            // 理论上不会发生
            e.printStackTrace();
            return null;
        }
    }

}
