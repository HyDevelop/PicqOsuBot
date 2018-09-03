package cc.moecraft.icq.plugins.osubot.database.service.impl;

import io.jboot.aop.annotation.Bean;
import cc.moecraft.icq.plugins.osubot.database.service.DwMapService;
import cc.moecraft.icq.plugins.osubot.database.model.DwMap;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

@Bean
@Singleton
public class DwMapServiceImpl extends JbootServiceBase<DwMap> implements DwMapService {

}