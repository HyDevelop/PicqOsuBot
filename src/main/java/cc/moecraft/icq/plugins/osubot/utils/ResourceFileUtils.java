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

    /**
     * 从缓存读一个resource
     * @param resourceClass 带这个resource的类
     * @param path 资源路径
     * @return 读出来的字符串
     */
    public static String readResource(Class resourceClass, String path)
    {
        if (cachedResource.containsKey(resourceClass))
        {
            Map<String, String> classResourceMap = cachedResource.get(resourceClass);

            if (classResourceMap.containsKey(path)) return classResourceMap.get(path);
            else classResourceMap.put(path, readResourceWithoutCache(resourceClass, path));
        }
        else cachedResource.put(resourceClass, new HashMap<>());

        return readResource(resourceClass, path);
    }

    private static String readResourceWithoutCache(Class resourceClass, String fileName)
    {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceClass.getClassLoader().getResourceAsStream(fileName))))
        {
            String line;
            while ((line = br.readLine()) != null) builder.append("\n").append(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return builder.toString();
    }
    private static File cacheDir;

    public static void initCache()
    {
        if (cacheDir != null && cacheDir.exists()) return;

        cacheDir = new File(String.format("./cache/cacheDir-%s/", System.currentTimeMillis()));

        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
            cacheDir.deleteOnExit();
        }
    }
}
