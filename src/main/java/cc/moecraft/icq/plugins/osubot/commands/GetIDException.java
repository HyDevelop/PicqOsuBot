package cc.moecraft.icq.plugins.osubot.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/09/03 创建!
 * Created by Hykilpikonna on 2018/09/03!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class GetIDException extends Exception
{
    private Operation operation;

    public enum Operation
    {
        SEND_HELP
    }
}
