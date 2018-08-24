package cc.moecraft.icq.plugins.osubot.commands;

import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;
import cn.hutool.http.HttpException;
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
    private static final boolean pmOnly = true;
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
        catch (UserNotFoundException e)
        {
            return "未找到用户: " + e.requestedUserIndicator.replace("%20", " ");
        }
        catch (HttpException | IOException e)
        {
            e.printStackTrace();
            return "查询失败, 网络访问异常... 请重试w";
        }
        catch (RuntimeException e)
        {
            if (e.getCause() instanceof TimeoutException) return "查询失败, 网络连接超时... 请重试w";
            throw e;
        }
    }

    public abstract String runOsu(EventMessage event, User user, String command, ArrayList<String> args)
            throws UserNotFoundException, HttpException, IOException;

    public abstract String help(String command);

    public void logComplete(String nick, String username, long startTime)
    {
        Main.getInstance().getLogger().log(String.format("%s - 用户 %s 查询 %s 完毕, 耗时 %sms",
                name.toUpperCase(), nick, username, System.currentTimeMillis() - startTime));
    }

    public String getImageMessage(File imageFile)
    {
        if (imageFile == null) return GeneralMessages.FAILED_NULL;
        return new ComponentImage("file://" + imageFile.getAbsolutePath()).toString();
    }
}
