package cc.moecraft.icq.plugins.osubot.database;

import cc.moecraft.icq.plugins.osubot.osu.Mode;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.ModeNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.exceptions.UserNotFoundException;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUserData;
import cc.moecraft.icq.plugins.osubot.osu.webapi.OWAUtils;
import cc.moecraft.utils.ReflectUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 此类由 Hykilpikonna 在 2018/08/09 创建!
 * Created by Hykilpikonna on 2018/08/09!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class Database
{
    private Connection connection;

    @Getter
    private final String host, database, username, password;

    @Getter
    private final int port;

    public void openConnection() throws SQLException, ClassNotFoundException
    {
        if (connection != null && !connection.isClosed()) return;

        synchronized (this)
        {
            if (connection != null && !connection.isClosed()) return;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
        }
    }

    public DataEntryPojo getEntryByQQ(long qq) throws UserNotFoundException, SQLException
    {
        try
        {
            String sql = "select * from user_settings where qq_id=" + qq;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            long osuId;
            String osuName;
            Mode defaultMode;

            if (resultSet.next())
            {
                osuId = resultSet.getLong("osu_id");
                osuName = resultSet.getString("osu_name");
                defaultMode = Mode.parse(resultSet.getString("default_mode"));
            }
            else throw new UserNotFoundException(String.valueOf(qq), "sql");

            return new DataEntryPojo(qq, osuId, osuName, defaultMode);
        }
        catch (ModeNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void putEntry(DataEntryPojo entry) throws SQLException
    {
        String sql = "insert into user_settings (qq_id, osu_id, osu_name, default_mode) values (%qq%, %osuId%, '%osuName%', '%defaultMode%') " +
                "on duplicate key update osu_id = %osuId%, osu_name = '%osuName%', default_mode = '%defaultMode%'";
        sql = ReflectUtils.replaceReflectVariables(entry, sql, false, false);
        //sql = sql.replace("%{defaultMode}", entry.getDefaultMode().toString());

        connection.createStatement().executeUpdate(sql);
    }

    public void putUser(long qq, String osuName) throws UserNotFoundException, SQLException
    {
        OWAUserData userData = OWAUtils.getUserData(osuName);
        putUser(qq, userData);
    }

    public void putUser(long qq, OWAUserData userData) throws SQLException
    {
        try
        {
            putEntry(new DataEntryPojo(qq, userData.getId(), userData.getUsername(), Mode.parse(userData.getPlaymode())));
        }
        catch (ModeNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
