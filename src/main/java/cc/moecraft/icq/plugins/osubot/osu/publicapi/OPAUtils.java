package cc.moecraft.icq.plugins.osubot.osu.publicapi;

import cc.moecraft.icq.plugins.osubot.Main;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.data.OPADataBase;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPAParamsBase;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.OPAUserParams;
import cc.moecraft.icq.plugins.osubot.osu.publicapi.parameters.tags.OPAParameter;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.JsonEmptyException;
import cc.moecraft.utils.DownloadUtils;
import cc.moecraft.utils.JsonUtils;
import cc.moecraft.utils.ReflectUtils;
import cc.moecraft.utils.URLBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/04/23 创建!
 * Created by Hykilpikonna on 2018/04/23!
 * Github: https://github.com/hykilpikonna
 * Meow!
 */
@AllArgsConstructor
public class OPAUtils
{
    private static final String BASE_URL = "http://osu.ppy.sh/api/get_";

    /**
     * 判断一个用户名在Osu官方数据库里是否存在
     * @param username 用户名
     * @return 是否存在
     */
    public boolean isUserExisting(String username)
    {
        JsonArray array = getJsonElementFromParameter(OPAUserParams.builder().u(username).build()).getAsJsonArray();
        return !(array.size() == 0);
    }

    /**
     * 用HTTP参数直接获取数据类对象
     * @param parameter HTTP参数
     * @return 数据类对象
     */
    public static ArrayList<OPADataBase> get(OPAParamsBase parameter) throws JsonEmptyException
    {
        JsonElement jsonElement = getJsonElementFromParameter(parameter);

        if (jsonElement.isJsonNull()) throw new JsonEmptyException();

        JsonArray jsonArray;

        // 在Osu官方API获取的话可能获取到Array
        if (jsonElement.isJsonArray()) jsonArray = jsonElement.getAsJsonArray();
        else if (jsonElement.isJsonObject()) jsonArray = JsonUtils.toJsonArray(jsonElement);
        else throw new JsonEmptyException(); // 既不是JsonArray也不是JsonObject的情况

        ArrayList<OPADataBase> data = new ArrayList<>();

        // 赋值
        for (JsonElement element : jsonArray)
        {
            data.add(new Gson().fromJson(element, (Type) parameter.dataStorageClass()));
        }

        return data;
    }

    /**
     * 用HTTP参数获取JSONObject
     *
     * @param parameter HTTP参数
     * @return JSON对象
     */
    public static JsonElement getJsonElementFromParameter(OPAParamsBase parameter)
    {
        boolean completeURL = parameter.subURL().contains("%COMPLETE_URL%");
        URLBuilder urlBuilder = new URLBuilder(completeURL ? parameter.subURL().replaceFirst("%COMPLETE_URL%", "") + "?" : BASE_URL + parameter.subURL() + "?");

        // 添加 APIKey
        if (!completeURL) urlBuilder.addParameter("k", Main.getApiKey());

        for (Field field : parameter.getClass().getDeclaredFields())
        {
            try
            {
                if (field.isAnnotationPresent(OPAParameter.class))
                {
                    Object value = ReflectUtils.getValue(field, parameter);

                    if (value != null) urlBuilder.addParameter(field.getName(), value.toString());
                    else if (field.getAnnotation(OPAParameter.class).required())
                        throw new RuntimeException("必要的参数是null: " + field.getName());
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace(); // 这里是程序错误, 所以直接输出处理
            }
        }

        return DownloadUtils.getJsonElementFromURL(urlBuilder.toString(), "utf-8");
    }
}
