package cc.moecraft.icq.plugins.osubot;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.pluginmanager.plugin.IcqPlugin;
import cc.moecraft.icq.plugins.osubot.browser.skills.UserSkillsImageBrowserManager;
import cc.moecraft.icq.plugins.osubot.browser.stats.UserStatsImageBrowserManager;
import cc.moecraft.icq.plugins.osubot.commands.CommandHelp;
import cc.moecraft.icq.plugins.osubot.commands.CommandSkills;
import cc.moecraft.icq.plugins.osubot.commands.CommandStats;
import cc.moecraft.logger.HyLogger;
import lombok.Getter;

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
    private UserStatsImageBrowserManager userStatsImageBrowserManager;

    @Getter
    private UserSkillsImageBrowserManager userSkillsImageBrowserManager;

    @Override
    public void onEnable()
    {
        // 加载插件的时候会运行这个方法
        instance = this;

        initUserStatsImageUtils(getLogger());
    }

    @Override
    public void onDisable()
    {
        // 卸载插件的时候会运行这个方法
    }
    
    public void initUserStatsImageUtils(HyLogger logger)
    {
        logger.timing.init();
        logger.log("正在初始化渲染器...");
        userStatsImageBrowserManager = new UserStatsImageBrowserManager();
        userSkillsImageBrowserManager = new UserSkillsImageBrowserManager();
        logger.log("初始化完成! 耗时:");
        logger.timing.time();
        logger.timing.reset();
    }

    @Override
    public IcqCommand[] commands()
    {
        return new IcqCommand[]
                {
                        new CommandStats(),
                        new CommandSkills(),
                        new CommandHelp()
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
