package cc.moecraft.icq.plugins.osubot.utils;

import cc.moecraft.icq.plugins.osubot.Main;
import org.apache.commons.io.FilenameUtils;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.utils.FileUtils.createDir;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ResourceFileUtils
{
    private static final Map<Class, Map<String, String>> cachedResource = new HashMap<>();

    /**
     * 从resources导出
     * @param resourceClass 带resources的类
     * @throws IOException 复制错误
     */
    public static void copyResource(Class resourceClass, String fileName, File toFile) throws IOException, NullPointerException
    {
        createDir(toFile.getParent());
        InputStream resourceAsStream = resourceClass.getClassLoader().getResourceAsStream(fileName);
        Files.copy(resourceAsStream, Paths.get(toFile.getAbsolutePath()));
    }

    public static String readResource(Class resourceClass, String fileName, String... variablesAndReplacements)
    {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceClass.getClassLoader().getResourceAsStream(fileName))))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                for (int i = 0; i < variablesAndReplacements.length; i += 2)
                {
                    line = line.replace("%{" + variablesAndReplacements[i] + "}", variablesAndReplacements[i + 1]);
                }

                builder.append("\n").append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
