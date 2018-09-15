package cc.moecraft.icq.plugins.osubot.osu.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor @Data
public class UserNotFoundException extends Exception
{
    public final String requestedUserIndicator;
    public final String html;
}
