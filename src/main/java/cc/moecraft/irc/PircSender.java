package cc.moecraft.irc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.output.OutputIRC;

import java.io.IOException;

/**
 * 此类由 Hykilpikonna 在 2018/08/11 创建!
 * Created by Hykilpikonna on 2018/08/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class PircSender
{
    @Getter
    private PircBotX pircBotX;

    public PircSender(String host, int port, String username, String password)
    {
        Configuration configuration = new Configuration.Builder()
                .addServer(host, port)
                .setName(username)
                .setServerPassword(password)
                .buildConfiguration();

        pircBotX = new PircBotX(configuration);
    }

    public void send(String to, String content)
    {
        to = to.replace(" ", "_").replace("%20", "_");
        rawSend(to, content);
    }

    public void rawSend(String to, String content)
    {
        pircBotX.sendIRC().message(to, content);
    }

    public void openConnection()
    {
        Thread thread = new Thread(new BotRunnable(pircBotX), "Thread-PircBotX");
        thread.start();
    }

    public void stop()
    {
        pircBotX.stopBotReconnect();
    }

    @AllArgsConstructor
    private static class BotRunnable implements Runnable
    {
        private PircBotX bot;

        @Override
        public void run()
        {
            try
            {
                bot.startBot();
            }
            catch (IOException | IrcException e)
            {
                e.printStackTrace();
                System.out.println("启动失败");
            }
        }
    }
}
