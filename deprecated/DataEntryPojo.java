package cc.moecraft.icq.plugins.osubot.database;

import cc.moecraft.icq.plugins.osubot.osu.Mode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
@Data
public class DataEntryPojo
{
    private final long qq;
    private final long osuId;
    private final String osuName;
    private Mode defaultMode;
}
