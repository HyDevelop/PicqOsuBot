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
}
