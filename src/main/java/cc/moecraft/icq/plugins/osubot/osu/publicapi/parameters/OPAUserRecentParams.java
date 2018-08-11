package cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters;

import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserRecentData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.tags.OPAParameter;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/04 创建!
 * Created by Hykilpikonna on 2018/05/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class OPAUserRecentParams extends OPAParamsBase
{
    // 必要的参数
    @OPAParameter(required = true)
    private String u;

    // 不必要的参数
    @OPAParameter(required = false)
    private String m;

    @OPAParameter(required = false)
    private String type;

    @OPAParameter(required = false)
    private String limit;

    @Override
    public String subURL()
    {
        return "user_recent";
    }

    @Override
    public Class dataStorageClass()
    {
        return OPAUserRecentData.class;
    }

    @Override
    public ReturnDataType returnDataType()
    {
        return ReturnDataType.LIST;
    }
}
