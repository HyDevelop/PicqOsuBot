package cc.moecraft.icq.plugins.osubot.database;

/**
 * 此类由 Hykilpikonna 在 2018/09/02 创建!
 * Created by Hykilpikonna on 2018/09/02!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ServiceInstanceManager
{
    public static <T> T get(Class<T> service)
    {
        try
        {
            return service.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
