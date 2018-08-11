package cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters;

import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OsuTrackData;
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
public class OsuTrackParams extends OPAParamsBase
{
    @OPAParameter(required = true)
    private String user;

    @OPAParameter(required = true)
    private String mode;

    @Override
    public String subURL()
    {
        return "%COMPLETE_URL%https://ameobea.me/osutrack/api/get_changes.php";
    }

    @Override
    public Class dataStorageClass()
    {
        return OsuTrackData.class;
    }

    @Override
    public ReturnDataType returnDataType()
    {
        return ReturnDataType.OBJECT;
    }
}
