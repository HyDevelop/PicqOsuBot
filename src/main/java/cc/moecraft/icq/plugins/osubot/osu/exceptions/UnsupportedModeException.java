package cc.moecraft.icq.plugins.osubot.osu.exceptions;

import cc.moecraft.icq.plugins.osubot.osu.Mode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @Data
public class UnsupportedModeException extends Exception
{
    private Mode mode;
}
