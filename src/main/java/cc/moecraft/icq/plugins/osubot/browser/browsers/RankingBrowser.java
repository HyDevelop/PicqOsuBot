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

    public RankingBrowser()
    {
        super(Math.round(1030 * scale), Math.round(848 * scale));
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
}
