package cc.moecraft.icq.plugins.osubot.commands;

import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;
import cn.hutool.http.HttpException;
import com.rabbitmq.client.AMQP;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public abstract class OsuCommandBase implements EverywhereCommand
{
    private static final boolean pmOnly = false;
    private final int minArgsSize;
    private final String name;

    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        if (pmOnly)
            if (event instanceof EventGroupMessage || event instanceof EventDiscussMessage) return "因为最近被举报, 在群里发图会被封号. 请私聊使用bot.";
        if (args.size() < minArgsSize) return help(command);

        try
        {
            return runOsu(event, user, command, args);
        }
        catch (GetIDException e)
        {
            switch (e.getOperation())
            {
                case SEND_HELP: return help(command);
                default: return "喵w?";
            }
        }
        catch (UserNotFoundException e)
        {
            return "未找到用户: " + e.requestedUserIndicator.replace("%20", " ");
        }
        catch (HttpException | IOException e)
        {
            e.printStackTrace();
            return "执行失败, 网络访问异常... 请重试w";
        }
        catch (RuntimeException e)
        {
            if (e.getCause() instanceof TimeoutException) return "执行失败, 网络连接超时... 请重试w";
            throw e;
        }
        catch (Exception e)
        {
            return "执行失败: " + e.getLocalizedMessage();
        }
    }

    public abstract String runOsu(EventMessage event, User user, String command, ArrayList<String> args)
            throws Exception;

    public abstract String help(String command);

    protected void logComplete(String nick, String username, long startTime)
    {
        logComplete(String.format("用户 %s 查询 %s 完毕", nick, username), startTime);
    }

    protected void logComplete(String message, long startTime)
    {
        Main.getInstance().getLogger().log(String.format("%s - %s, 耗时 %sms", name.toUpperCase(), message, System.currentTimeMillis() - startTime));
    }

    protected void logComplete(String message)
    {
        Main.getInstance().getLogger().log(name.toUpperCase() + " - " + message);
    }

    protected String getImageMessage(File imageFile)
    {
        if (imageFile == null) return GeneralMessages.FAILED_NULL;
        return new ComponentImage("file://" + imageFile.getAbsolutePath()).toString();
    }

    protected String getDatabasedUsername(ArrayList<String> args, int index, User user) throws GetIDException
    {
        if (args.size() > index)
        {
            // 用户输入了用户名
            //System.out.println("0");
            return ArrayUtils.getTheRestArgsAsString(args, index);
        }
        else
        {
           // System.out.println("1");
            HoUserSettings qqIdUserSettings = ServiceInstanceManager.get(HoUserSettingsServiceImpl.class).findByQq(user.getId());
            //System.out.println("2");
            //System.out.println(qqIdUserSettings);
            if (qqIdUserSettings == null) throw new GetIDException(GetIDException.Operation.SEND_HELP);
            //System.out.println("3");
            return qqIdUserSettings.getOsuName();
        }
    }
}
