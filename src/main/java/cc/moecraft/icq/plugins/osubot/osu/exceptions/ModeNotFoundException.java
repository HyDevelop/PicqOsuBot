package cc.moecraft.icq.plugins.osubot.osu.exceptions;

import lombok.AllArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class ModeNotFoundException extends Exception
{
    public final String requestedMode;
}
