package cc.moecraft.icq.plugins.osubot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}
