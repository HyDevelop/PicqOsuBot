package cc.moecraft.icq.plugins.osubot.browser;

import com.teamdev.jxbrowser.chromium.BeforeSendHeadersParams;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * 此类由 Hykilpikonna 在 2018/08/08 创建!
 * Created by Hykilpikonna on 2018/08/08!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class MobileBrowserUADelegate extends DefaultNetworkDelegate
{
    @Override
    public void onBeforeSendHeaders(BeforeSendHeadersParams params)
    {
        params.getHeadersEx().setHeader("user-agent", "Mozilla/5.0 (Linux; Android 8.0.0; MIX 2S Build/OPR1.170623.032) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.85 Mobile Safari/537.36");
        super.onBeforeSendHeaders(params);
    }
}
