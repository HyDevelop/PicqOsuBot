package cc.moecraft.icq.plugins.osubot.utils;

import com.jfinal.config.JFinalConfig;
import com.jfinal.core.JFinal;
import io.jboot.Jboot;
import io.jboot.web.JbootAppConfig;
import lombok.Getter;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/09/02 创建!
 * Created by Hykilpikonna on 2018/09/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class JbootUtils
{
    /**
     * 初始化JFinal.
     * 用这个方法初始化之后就可以不用启动HTTP服务器的情况下调用数据库啦!
     */
    public static void initializeJFinalSafe()
    {
        try
        {
            initializeJFinal();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initializeJFinal() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        // 找到JFinal的Config类的configJfinal(JFinalConfig jfinalConfig)方法
        Class<?> configClass = Class.forName("com.jfinal.core.Config");
        Method configJFinal = configClass.getDeclaredMethod("configJFinal", JFinalConfig.class);

        // 因为它的访问级别是Package Private, 需要setAccessible(true)才能调用
        configJFinal.setAccessible(true);

        // 用Jboot的配置文件调用
        configJFinal.invoke(null, new JbootAppConfig());
    }
}
