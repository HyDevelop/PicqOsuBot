package cc.moecraft.icq.plugins.osubot.utils;

import com.jfinal.config.JFinalConfig;
import com.jfinal.core.JFinal;
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

    public static void setDevMode(boolean devMode)
    {
        JFinal.me().getConstants().setDevMode(devMode);
    }

    private static SystemOutFilter systemOutFilter = null;
    public static void enableSystemOutFilter()
    {
        if (systemOutFilter != null) return;
        System.setOut(systemOutFilter = new SystemOutFilter(System.out));
    }

    /**
     * 关闭Jboot的SQL指令信息报告
     */
    public static void disableSqlReport()
    {
        enableSystemOutFilter();
        systemOutFilter.getDisabledStackTraces().add("com.jfinal.plugin.activerecord.SqlReporter.invoke:56");
    }

    /**
     * 系统输出过滤,
     * 用来防止Jboot输出Sql指令信息刷屏什么的_(:з」∠)_
     */
    public static class SystemOutFilter extends PrintStream
    {
        @Getter
        private ArrayList<String> disabledStackTraces = new ArrayList<>();

        private SystemOutFilter(OutputStream out)
        {
            super(out);
        }

        /**
         * 过滤StackTrace
         * @return 是否通过过滤
         */
        private boolean verify()
        {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];

            String stClass = stackTrace.getClassName();
            String stMethod = stackTrace.getMethodName();
            String stLine = stackTrace.getLineNumber() + "";
            String stFull = stClass + "." + stMethod + ":" + stLine;

            for (String disabledStackTrace : disabledStackTraces)
                if (disabledStackTrace.equals(stFull)) return false;
            return true;
        }

        @Override
        public void println(String s)
        {
            if (verify()) super.println(s);
        }
    }
}
