package cc.moecraft.icq.plugins.osubot.browser.browsers;

import cc.moecraft.icq.plugins.osubot.browser.OsuBrowser;
import cc.moecraft.icq.plugins.osubot.commands.setid.VerificationStates;
import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.OPAWrapper;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserData;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import cc.moecraft.icq.user.Group;
import cc.moecraft.utils.MathUtils;
import cc.moecraft.utils.ReflectUtils;
import cc.moecraft.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 此类由 Hykilpikonna 在 2018/09/04 创建!
 * Created by Hykilpikonna on 2018/09/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class RankingBrowser extends OsuBrowser
{
    public static final float scale = 1.5f;
    public static final int entriesPerPage = 20;
    public static final int pageDisplayFront = 3;
    public static final int pageDisplayEnd = 3;

    public RankingBrowser()
    {
        super(Math.round(1030 * scale), Math.round(848 * scale));
    }

    public File render(Group group, IcqHttpApi api, int mode, int page) throws IOException
    {
        ReturnListData<RGroupMemberInfo> members = api.getGroupMemberList(group.getId());
    }

    public File render(Group group, ArrayList<UserData> userDataList, int modeCode, PageInfo page) throws IOException
    {
        Mode mode = Mode.parse(modeCode);
        String mainHtml = getHtml("web/ranking/ranking-template.html");
        StringBuilder entries = new StringBuilder();
        StringBuilder pages = new StringBuilder();

        // 获取用户
        for (int i = 0; i < userDataList.size(); i++)
        {
            UserData userData = userDataList.get(i);
            OPAUserData osu = userData.osu;
            RGroupMemberInfo qq = userData.qq;
            String html = StringUtils.replaceVariables(getHtml("web/ranking/ranking-entry.html"), "temp", "temp",
                    "rank", osu.getPpRank(),
                    "group.rank", page.getStart() + i + 1,
                    "name", osu.getUsername(),
                    "country", osu.getCountry(),
                    "qq", qq.getUserId(),
                    "acc", MathUtils.round(osu.getAccuracy(), 1),
                    "pp", MathUtils.round(osu.getPpRaw(), 1),
                    "pc", StringUtils.toNumberWithComma(osu.getPlayCount()),
                    "ss", StringUtils.toNumberWithComma(osu.getCountRankSs() + osu.getCountRankSsh()),
                    "s", StringUtils.toNumberWithComma(osu.getCountRankS() + osu.getCountRankSh()),
                    "a", StringUtils.toNumberWithComma(osu.getCountRankA())
            );
            entries.append(html);
        }

        // 获取翻页
        for (int i = 1; i <= page.max; i++)
        {
            if (page.current > i && page.current - i < pageDisplayFront) pages.append(getPageHtml(i, false));
            else if (page.current < i && i - page.current < pageDisplayEnd) pages.append(getPageHtml(i, false));
            else pages.append(getPageHtml(i, true));
        }

        mainHtml = StringUtils.replaceVariables(mainHtml, "temp", "temp",
                "mode", mode.getName(),
                "group.name", group.getInfo().getGroupName(),
                "group.id", group.getId(),
                "entries", entries,
                "pages", pages
        );

        loadHtml(mainHtml);
        sleep(1000);
        setRunning(false);
        return render(0, 0, 1016, 142 + userDataList.size() * 35, scale); //TODO: test dis
    }

    @Override
    public String name()
    {
        return "ranking";
    }

    @Data @AllArgsConstructor
    private static class PageInfo
    {
        private int max;
        private int current;
        private int start;
        private int end;
    }

    @Data @AllArgsConstructor
    private static class UserData implements Comparable<UserData>
    {
        private OPAUserData osu;
        private RGroupMemberInfo qq;

        @Override
        public int compareTo(UserData o)
        {
            return osu.compareTo(o.osu);
        }
    }

    private String getPageHtml(int page, boolean active)
    {
        return (active ? "<span class=\"paginator__page paginator__page--current\">" : "<span class=\"paginator__page\">") + page + "</span>";
    }
}
