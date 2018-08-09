package cc.moecraft.icq.plugins.osubot;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.pluginmanager.plugin.IcqPlugin;
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

    @Override
    public void onEnable()
    {
        // 加载插件的时候会运行这个方法
        instance = this;
    }

    @Override
    public void onDisable()
    {
        // 卸载插件的时候会运行这个方法
    }
    @Override
    public IcqCommand[] commands()
    {
        return new IcqCommand[]
                {
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
