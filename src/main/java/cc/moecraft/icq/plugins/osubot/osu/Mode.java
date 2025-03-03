package cc.moecraft.icq.plugins.osubot.osu;

import cc.moecraft.icq.plugins.osubot.osu.exceptions.ModeNotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public enum Mode
{
    STD(0, "STD", "Standard", "S", "Osu", "0", "标准"),
    TAIKO(1, "Taiko", "T", "Tk", "1", "太鼓"),
    CTB(2, "CTB", "Catch", "CatchTheBeat", "Catch The Beat", "C", "2", "Fruits", "接水果"),
    MANIA(3, "Mania", "M", "3", "下落", "骂娘");

    @Getter
    private int code;

    @Getter
    private String name;

    @Getter
    private String[] alias;

    Mode(int code, String ... alias)
    {
        this.code = code;
        this.name = alias[0];
        this.alias = alias;
        for (String name : alias) Constants.lowercaseNameIndex.put(name.toLowerCase(), this);
    }

    /**
     * 用名字获取模式
     * @param name 名字
     * @return 模式
     * @throws ModeNotFoundException 未找到
     */
    public static Mode parse(String name) throws ModeNotFoundException
    {
        name = name.toLowerCase();
        if (Constants.lowercaseNameIndex.containsKey(name)) return Constants.lowercaseNameIndex.get(name);
        throw new ModeNotFoundException(name);
    }

    /**
     * 用数字获取模式
     * @param code 数字
     * @return 模式
     */
    public static Mode parse(int code)
    {
        if (code < 0 || code > 3) return null;
        try
        {
            return parse(String.valueOf(code));
        }
        catch (ModeNotFoundException e)
        {
            // Never happens
            e.printStackTrace();
            return null;
        }
    }

    private static class Constants
    {
        static final Map<String, Mode> lowercaseNameIndex = new HashMap<>();
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
