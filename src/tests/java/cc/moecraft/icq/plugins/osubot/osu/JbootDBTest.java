package cc.moecraft.icq.plugins.osubot.osu;

import cc.moecraft.icq.plugins.osubot.database.ServiceInstanceManager;
import cc.moecraft.icq.plugins.osubot.database.model.HoUserSettings;
import cc.moecraft.icq.plugins.osubot.database.service.HoUserSettingsService;
import cc.moecraft.icq.plugins.osubot.database.service.impl.HoUserSettingsServiceImpl;
import cc.moecraft.icq.plugins.osubot.utils.JbootUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 此类由 Hykilpikonna 在 2018/09/02 创建!
 * Created by Hykilpikonna on 2018/09/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class JbootDBTest
{
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException
    {
        HoUserSettingsService service = ServiceInstanceManager.get(HoUserSettingsServiceImpl.class);
        //System.out.println(service.findByOsu(5093373));
        //System.out.println(service.findByQq(565656));

        HoUserSettings model = service.findByOsu(5093373);
        model.setVerificationCode(String.valueOf(999999));
        model.saveOrUpdate();
    }
}
