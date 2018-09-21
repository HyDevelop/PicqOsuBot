package cc.moecraft.icq.plugins.osubot.commands.osu;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.browser.browsers.RankingBrowser;
import cc.moecraft.icq.plugins.osubot.browser.browsers.RecentBrowser;
import cc.moecraft.icq.plugins.osubot.commands.OsuCommandBase;
import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.ModeNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.RecentScoreNotEnoughException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UnsupportedModeException;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.User;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/09/04 创建!
 * Created by Hykilpikonna on 2018/09/04!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandRanking extends OsuCommandBase
{
    public CommandRanking()
    {
        super(1, "ranking");
    }

    /**
     * 用法:
     *
     * /top mode=模式 page=页
     *
     * @param event 事件
     * @param user 用户
     * @param command 指令
     * @param args 参数
     * @return 回复
     * @throws Exception 异常
     */
    @Override
    public String runOsu(EventMessage event, User user, String command, ArrayList<String> args) throws Exception
    {
        if (!(event instanceof EventGroupMessage)) return "请在群里执行此指令w";
        long startTime = System.currentTimeMillis();

        Mode mode;
        int page = 1;

        try
        {
            mode = Mode.parse(args.get(0));
        }
        catch (ModeNotFoundException e)
        {
            return "你输入的 " + e.requestedMode + " 这个Mode是什么啊? (可以输入 osu taiko catch mania)";
        }

        if (args.size() > 1)
        {
            try
            {
                page = Integer.parseInt(args.get(1));
            }
            catch (NumberFormatException e)
            {
                return "你输入的 " + args.get(1) + " 是第几页哇?";
            }
        }

        IcqHttpApi api = event.getHttpApi();
        Group group = ((EventGroupMessage) event).getGroup();

        api.sendGroupMsg(group.getId(), "正在查询群排名... 请稍后ww\n提示: 需要绑定ID并且验证才能计入群排名哦!\n详细说明请输入 -setid");

        File imageFile = Main.getBrowserManager().getBrowser(RankingBrowser.class).render(group, event.getHttpApi(), mode.getCode(), page);
        logComplete(String.format("用户 %s 查询群 %s 排名完毕", user.getInfo().getNickname(), group.getId()), startTime);

        api.sendGroupMsg(group.getId(), getImageMessage(imageFile));

        return null;
    }

    @Override
    public String help(String command)
    {
        return "查询群内排名: -" + command + " [模式] <页数 (默认第一页)>";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("ranking", "top", "群排名");
    }
}
