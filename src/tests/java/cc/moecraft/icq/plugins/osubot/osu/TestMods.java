package cc.moecraft.icq.plugins.osubot.osu;

/**
 * 此类由 Hykilpikonna 在 2018/08/27 创建!
 * Created by Hykilpikonna on 2018/08/27!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class TestMods
{
    public static void main(String[] args)
    {
        Mods mods = new Mods(0); // 创建什么都没有的mod组

        mods.add(Mod.HardRock, Mod.Hidden); // 添加 HD HR

        mods.contains(Mod.HardRock); // 判断有没有 HR (true

        mods.remove(Mod.HardRock); // 移除 HR

        mods.contains(Mod.HardRock); // 判断有没有 HR (false

        mods.toDec(); // 获取修改后的10进制值

        mods.toArray(); // 获取为Mod对象数组

        mods = Mods.parseFromString("HD DT HR"); // 从字符串解析

        mods = Mods.parseFromShortString("HDDTHR"); // 从短字符串解析
    }
}
