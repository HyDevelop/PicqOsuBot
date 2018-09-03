package cc.moecraft.icq.plugins.osubot.database.service.impl;

import io.jboot.aop.annotation.Bean;
import cc.moecraft.icq.plugins.osubot.database.service.HoUserSettingsService;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

@Bean
@Singleton
public class HoUserSettingsServiceImpl extends JbootServiceBase<HoUserSettings> implements HoUserSettingsService
{
    @Override
    public HoUserSettings findByOsu(long osuId)
    {
        return getDao().findFirstByColumn("osu_id", osuId);
    }

    @Override
    public HoUserSettings findByQq(long qqId)
    {
        return getDao().findFirstByColumn("qq_id", qqId);
    }
}