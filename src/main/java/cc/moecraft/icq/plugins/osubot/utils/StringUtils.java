package cc.moecraft.icq.plugins.osubot.utils;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class StringUtils
{
    public static String replaceVariables(String original, Object ... variablesAndReplacements)
    {
        StringBuilder builder = new StringBuilder();

        for (String line : original.split("\n"))
        {
            for (int i = 0; i < variablesAndReplacements.length; i += 2)
                line = line.replace("%{" + String.valueOf(variablesAndReplacements[i]) + "}",
                        String.valueOf(variablesAndReplacements[i + 1]));

            builder.append("\n").append(line);
        }

        return builder.toString();
    }

    public static String toReadableNumber(Object number)
    {
        String original = String.valueOf(number);
        StringBuilder result = new StringBuilder();

        char[] charArray = original.toCharArray();
        for (int i = 0; i < charArray.length; i++)
        {
            if (i % 3 == 0 && i != 0) result.insert(0, ",");
            result.insert(0, charArray[charArray.length - i - 1]);
        }

        return String.valueOf(result);
    }

    public static String limitSize(String original, int size)
    {
        return original.length() <= size ? original : original.substring(0, size - 3) + "...";
    }
}
