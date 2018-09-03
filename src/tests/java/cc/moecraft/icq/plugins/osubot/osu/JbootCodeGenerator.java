package cc.moecraft.icq.plugins.osubot.osu;

import io.jboot.codegen.model.JbootModelGenerator;
import io.jboot.codegen.service.JbootServiceGenerator;

/**
 * 此类由 Hykilpikonna 在 2018/09/02 创建!
 * Created by Hykilpikonna on 2018/09/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class JbootCodeGenerator
{
    public static void main(String[] args)
    {
        String modelPackage = "cc.moecraft.icq.plugins.osubot.database.model";
        String servicePackage = "cc.moecraft.icq.plugins.osubot.database.service";

        JbootModelGenerator.run(modelPackage);
        JbootServiceGenerator.run(servicePackage, modelPackage);
    }
}
