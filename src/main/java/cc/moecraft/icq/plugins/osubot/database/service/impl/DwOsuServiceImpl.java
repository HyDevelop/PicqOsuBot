package cc.moecraft.icq.plugins.osubot.database.service.impl;

import io.jboot.aop.annotation.Bean;
import cc.moecraft.icq.plugins.osubot.database.service.DwOsuService;
import cc.moecraft.icq.plugins.osubot.database.model.DwOsu;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

@Bean
@Singleton
public class DwOsuServiceImpl extends JbootServiceBase<DwOsu> implements DwOsuService {

}