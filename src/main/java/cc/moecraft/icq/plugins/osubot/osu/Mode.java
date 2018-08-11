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
    STD(0, "STD", "Standard", "S", "Osu", "0"),
    TAIKO(1, "Taiko", "T", "1"),
    CTB(2, "CTB", "Catch", "CatchTheBeat", "Catch The Beat", "C", "2"),
    MANIA(3, "Mania", "M", "3");

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
    private static class Constants
    {
        static final Map<String, Mode> lowercaseNameIndex = new HashMap<>();
    }
}
