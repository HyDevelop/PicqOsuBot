package cc.moecraft.icq.plugins.osubot.osu.publicapi.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/02 创建!
 * Created by Hykilpikonna on 2018/05/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class OPABeatmapData extends OPADataBase
{
    @SerializedName("beatmapset_id")
    @Expose
    private long beatmapsetId;

    @SerializedName("beatmap_id")
    @Expose
    private long beatmapId;

    @SerializedName("approved")
    @Expose
    private int approved;

    @SerializedName("total_length")
    @Expose
    private int totalLength;

    @SerializedName("hit_length")
    @Expose
    private int hitLength;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("file_md5")
    @Expose
    private String fileMd5;

    @SerializedName("diff_size")
    @Expose
    private double diffSize;

    @SerializedName("diff_overall")
    @Expose
    private double diffOverall;

    @SerializedName("diff_approach")
    @Expose
    private double diffApproach;

    @SerializedName("diff_drain")
    @Expose
    private double diffDrain;

    @SerializedName("mode")
    @Expose
    private int mode;

    @SerializedName("approved_date")
    @Expose
    private String approvedDate;

    @SerializedName("last_update")
    @Expose
    private String lastUpdate;

    @SerializedName("artist")
    @Expose
    private String artist;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("creator")
    @Expose
    private String creator;

    @SerializedName("bpm")
    @Expose
    private double bpm;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("tags")
    @Expose
    private String tags;

    @SerializedName("genre_id")
    @Expose
    private int genreId;

    @SerializedName("language_id")
    @Expose
    private int languageId;

    @SerializedName("favourite_count")
    @Expose
    private int favouriteCount;

    @SerializedName("playcount")
    @Expose
    private long playcount;

    @SerializedName("passcount")
    @Expose
    private long passCount;

    @SerializedName("max_combo")
    @Expose
    private int maxCombo;

    @SerializedName("difficultyrating")
    @Expose
    private double difficultyRating;
}
