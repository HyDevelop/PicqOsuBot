package cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters;

import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPAUserData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.tags.OPAParameter;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/04/24 创建!
 * Created by Hykilpikonna on 2018/04/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 */
@EqualsAndHashCode(callSuper = true)
@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class OPAUserParams extends OPAParamsBase
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
    private String event_days;

    public OPAUserParams(String userIdOrName)
    {
        setU(userIdOrName);
    }

    @Override
    public String subURL()
    {
        return "user";
    }

    @Override
    public Class dataStorageClass()
    {
        return OPAUserData.class;
    }

    @Override
    public ReturnDataType returnDataType()
    {
        return ReturnDataType.SINGLETON_LIST;
    }
}
