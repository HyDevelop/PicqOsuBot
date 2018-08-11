package cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters;

import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserScoreData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.tags.OPAParameter;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/05 创建!
 * Created by Hykilpikonna on 2018/05/05!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Builder @AllArgsConstructor @Data @NoArgsConstructor
public class OPAUserScoreParams extends OPAParamsBase
{
    // 必要的参数
    @OPAParameter(required = true)
    private String b;

    // 不必要的参数
    @OPAParameter(required = false)
    private String u;

    @OPAParameter(required = false)
    private String m;

    @OPAParameter(required = false)
    private String mods;

    @OPAParameter(required = false)
    private String type;

    @OPAParameter(required = false)
    private String limit;

    @Override
    public String subURL()
    {
        return "scores";
    }

    @Override
    public Class dataStorageClass()
    {
        return OPAUserScoreData.class;
    }

    @Override
    public ReturnDataType returnDataType()
    {
        return ReturnDataType.LIST;
    }
}
