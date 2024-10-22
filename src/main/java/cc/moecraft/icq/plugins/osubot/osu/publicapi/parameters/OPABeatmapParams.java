package cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters;

import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPABeatmapData;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.tags.OPAParameter;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/03 创建!
 * Created by Hykilpikonna on 2018/05/03!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class OPABeatmapParams extends OPAParamsBase
{
    // 不必要的参数
    @OPAParameter(required = false)
    private String since; // return all beatmaps ranked or loved since this date. Must be a MySQL date.

    @OPAParameter(required = false)
    private String s; // specify a beatmapset_id to return metadata from.

    @OPAParameter(required = false)
    private String b; // specify a beatmap_id to return metadata from.

    @OPAParameter(required = false)
    private String u; // specify a user_id or a username to return metadata from.

    @OPAParameter(required = false)
    private String m; // mode (0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!mania). Optional, maps of all modes are returned by default.

    @OPAParameter(required = false)
    private String a; // specify whether converted beatmaps are included (0 = not included, 1 = included). Only has an effect if m is chosen and not 0. Converted maps show their converted difficulty rating. Optional, default is 0.

    @OPAParameter(required = false)
    private String h; // the beatmap hash. It can be used, for instance, if you're trying to get what beatmap has a replay played in, as .osr replays only provide beatmap hashes (example of hash: a5b99395a42bd55bc5eb1d2411cbdf8b). Optional, by default all beatmaps are returned independently from the hash.

    @OPAParameter(required = false)
    private String type; // specify if u is a user_id or a username. Use string for usernames or id for user_ids. Optional, default behaviour is automatic recognition (may be problematic for usernames made up of digits only).

    @OPAParameter(required = false)
    private String limit; // amount of results. Optional, default and maximum are 500.

    @Override
    public String subURL()
    {
        return "beatmaps";
    }

    @Override
    public Class dataStorageClass()
    {
        return OPABeatmapData.class;
    }

    @Override
    public ReturnDataType returnDataType()
    {
        return ReturnDataType.LIST;
    }
}
