package cc.moecraft.icq.plugins.osubot.osu.exceptions;

import lombok.AllArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/08/07 创建!
 * Created by Hykilpikonna on 2018/08/07!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class JsonNotFoundException extends Exception
{
    public final String html;
    public final String jsonName;
    public final Pattern pattern;
    public final Matcher matcher;
}
