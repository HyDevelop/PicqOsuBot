package cc.moecraft.icq.plugins.osubot.osu;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/08 创建!
 * Created by Hykilpikonna on 2018/05/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
@NoArgsConstructor
public class Mods
{
    private long modsInDEC = 0;

    /**
     * 封装构造器, 在构造之后直接添加mods
     * @param mods mods
     */
    public Mods(Mod ... mods)
    {
        this();
        add(mods);
    }

    /**
     * 判断一个Mod是否存在
     * @param mod mod
     * @return 是否存在
     */
    public boolean contains(Mod mod)
    {
        return (modsInDEC & mod.getBitwiseValue()) == mod.getBitwiseValue();
    }

    /**
     * 获取所有mod为ArrayList形式
     * @return 所有mod列表
     */
    public ArrayList<Mod> toArray()
    {
        ArrayList<Mod> result = new ArrayList<>();

        Mod.getModReferenceMap().forEach((k, v) ->
        {
            if (contains(v)) result.add(v);
        });

        return result;
    }

    /**
     * 获取所有mod为十进制数格式
     * @return 代表所有mod的十进制数
     */
    public long toDec()
    {
        return modsInDEC;
    }

    /**
     * 添加一个Mod
     * @param mod mod
     * @return 这个实例
     */
    public Mods add(Mod mod)
    {
        modsInDEC = modsInDEC | mod.getBitwiseValue();
        return this;
    }

    /**
     * 添加一个Mod的封装, 添加多个mod
     * @param mods mod
     * @return 这个实例
     */
    public Mods add(Mod ... mods)
    {
        for (Mod oneMod : mods) add(oneMod);
        return this;
    }

    /**
     * 移除一个Mod
     * @param mod mod
     * @return 这个实例
     */
    public Mods remove(Mod mod)
    {
        modsInDEC = modsInDEC ^ mod.getBitwiseValue();
        return this;
    }

    /**
     * 移除一个Mod的封装, 移除多个mod
     * @param mods mod
     * @return 这个实例
     */
    public Mods remove(Mod ... mods)
    {
        for (Mod oneMod : mods) remove(oneMod);
        return this;
    }

    /**
     * 获取所有mod合在一起的名字
     * Ex: "HD DT HR"
     *
     * @return 所有mod名字
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        ArrayList<Mod> modsArrayList = toArray();

        if (modsArrayList.size() == 0) return "";
        
        builder.append(modsArrayList.get(0).getShortName());
        modsArrayList.remove(0);

        modsArrayList.forEach(oneMod -> builder.append(" ").append(oneMod.getShortName()));

        return builder.toString();
    }

    /**
     * 从短名字转换为Mods
     * Ex: "HDDTHR"
     *
     * @param shortString 短名字
     * @return Mods对象
     */
    public static Mods parseFromShortString(String shortString)
    {
        String split = shortString.replaceAll("..(?!$)", "$0 ");

        return parseFromString(split);
    }

    /**
     * 从名字转换为Mods
     * Ex: "HD DT HR"
     *
     * @param name 名字
     * @return Mods对象
     */
    public static Mods parseFromString(String name)
    {
        String[] split = name.split(" ");

        Mods result = new Mods();

        for (String oneName : split)
        {
            if (Mod.getModReferenceMap().containsKey(oneName)) result.add(Mod.getModReferenceMap().get(oneName));
        }

        return result;
    }

    public String toHtml()
    {
        StringBuilder htmlBuilder = new StringBuilder();

        for (Mod mod : toArray())
        {
            htmlBuilder.append("<div class=\"mods__mod\"><div class=\"mods__mod-image\"><div class=\"mod mod--%{mod}\" title=\"Hidden\"></div></div></div>"
                    .replace("%{mod}", mod.getShortName()));
        }

        return htmlBuilder.toString();
    }
}
