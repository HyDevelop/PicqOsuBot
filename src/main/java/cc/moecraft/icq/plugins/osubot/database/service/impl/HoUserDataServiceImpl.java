package cc.moecraft.icq.plugins.osubot.database.service.impl;

import io.jboot.aop.annotation.Bean;
import cc.moecraft.icq.plugins.osubot.database.service.HoUserDataService;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserData;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

@Bean
@Singleton
public class HoUserDataServiceImpl extends JbootServiceBase<HoUserData> implements HoUserDataService
{
    @Override
    public HoUserData findByOsu(long osuId)
    {
        return getDao().findFirstByColumn("osu_id", osuId);
    }
}