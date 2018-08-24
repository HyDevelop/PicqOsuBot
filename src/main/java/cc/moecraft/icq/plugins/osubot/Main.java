package cc.moecraft.icq.plugins.osubot;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.pluginmanager.plugin.IcqPlugin;
import cc.moecraft.icq.plugins.osubot.browser.OsuBrowserManager;
import cc.moecraft.icq.plugins.osubot.commands.managing.CommandIrcServerInfo;
import cc.moecraft.icq.plugins.osubot.commands.managing.CommandIrcStatus;
import cc.moecraft.icq.plugins.osubot.commands.osu.*;
import cc.moecraft.icq.plugins.osubot.database.Database;
import cc.moecraft.irc.PircSender;
import cc.moecraft.logger.HyLogger;
import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;

import static cc.moecraft.icq.plugins.osubot.utils.ResourceFileUtils.*;

/**
 * 此类由 Hykilpikonna 在 2018/07/16 创建!
 * Created by Hykilpikonna on 2018/07/16!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class Main extends IcqPlugin
{
    @Getter
    private static OsuBrowserManager browserManager;

    @Getter
    private static String apiKey;

    @Getter
    private static Database database;

    @Getter
    private static PircSender pircSender;

    @Override
    public void onEnable()
    {
        // 加载插件的时候会运行这个方法
        instance = this;

        if (apiKey == null) apiKey = getConfig().getString("APIKey");
        if (database == null) database = new Database(
                getConfig().getString("Database-MySQL.Host"),
                getConfig().getString("Database-MySQL.Database"),
                getConfig().getString("Database-MySQL.Username"),
                getConfig().getString("Database-MySQL.Password"),
                getConfig().getInt("Database-MySQL.Port"));
        if (pircSender == null) pircSender = new PircSender(
                getConfig().getString("IRC.Host"),
                getConfig().getInt("IRC.Port"),
                getConfig().getString("IRC.Username"),
                getConfig().getString("IRC.Password"));

        init(getLogger());
    }

    @Override
    public void onDisable()
    {
        // 卸载插件的时候会运行这个方法
        pircSender.stop();
    }

    public void init(HyLogger logger)
    {
        try
        {
            logger.timing.init();

            logger.log("正在初始化渲染器...");
            initCache();
            cacheFile("web/bg-light.png");
            cacheFile("web/css/osu.css");
            cacheTarGz("web/images.tar.gz");
            logger.timing.timeAndReset();

            logger.log("正在初始化渲染器实例管理器...");
            browserManager = new OsuBrowserManager();
            logger.timing.timeAndReset();

            logger.log("正在连接数据库...");
            database.openConnection();
            logger.timing.timeAndReset();

            logger.log("正在连接IRC...");
            pircSender.openConnection();
            logger.timing.timeAndReset();

            logger.log("全部初始化完成!");
            logger.timing.timeAndReset();
            logger.timing.clear();
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IcqCommand[] commands()
    {
        return new IcqCommand[]
                {
                        new CommandStats(),
                        new CommandSkills(),
                        new CommandRecent(),
                        new CommandSetId(),
                        new CommandHelp(),

                        new CommandIrcServerInfo(),
                        new CommandIrcStatus()
                };
    }

    @Override
    public IcqListener[] listeners()
    {
        return new IcqListener[]
                {
                };
    }

    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }
}
