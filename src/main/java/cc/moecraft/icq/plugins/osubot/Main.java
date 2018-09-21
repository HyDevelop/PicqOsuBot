package cc.moecraft.icq.plugins.osubot;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.pluginmanager.plugin.IcqPlugin;
import cc.moecraft.icq.plugins.osubot.browser.OsuBrowserManager;
import cc.moecraft.icq.plugins.osubot.commands.irc.CommandIrcServerInfo;
import cc.moecraft.icq.plugins.osubot.commands.irc.CommandIrcStatus;
import cc.moecraft.icq.plugins.osubot.commands.osu.*;
import cc.moecraft.icq.plugins.osubot.commands.setid.CommandForceSetId;
import cc.moecraft.icq.plugins.osubot.commands.setid.CommandSetId;
import cc.moecraft.icq.plugins.osubot.commands.setid.CommandVerify;
import cc.moecraft.icq.plugins.osubot.utils.JbootUtils;
import cc.moecraft.irc.PircSender;
import cc.moecraft.logger.HyLogger;
import lombok.Getter;

import java.io.IOException;

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
    public static final String CONTACT_INFO = "qq:565656";

    @Getter
    private static OsuBrowserManager browserManager;

    @Getter
    private static String apiKey;

    @Getter
    private static PircSender pircSender;

    @Override
    public void onEnable()
    {
        // 加载插件的时候会运行这个方法
        instance = this;

        if (apiKey == null) apiKey = getConfig().getString("APIKey");
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

            logger.log("正在初始化Jboot数据库...");
            Thread.currentThread().setContextClassLoader(getClassLoader());
            JbootUtils.initializeJFinalSafe();
            Thread.currentThread().setContextClassLoader(null);
            JbootUtils.setDevMode(getBot().isDebug());
            if (!getBot().isDebug()) JbootUtils.disableSqlReport();
            logger.timing.timeAndReset();

            logger.log("正在连接IRC...");
            pircSender.openConnection();
            logger.timing.timeAndReset();

            logger.log("全部初始化完成!");
            logger.timing.timeAndReset();
            logger.timing.clear();
        }
        catch (IOException e)
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
                        new CommandRanking(),
                        new CommandHelp(),

                        new CommandSetId(),
                        new CommandVerify(),
                        new CommandForceSetId(),

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
