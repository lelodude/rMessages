package org.royaldev.royalmessages;

/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

This plugin was originally written by jkcclemens and is now taken over by Lelo.
If forked and not credited, alert me at bukkit.org!
*/

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class rMessages extends JavaPlugin {

    public String version;

    private final PListener playerListener = new PListener(this);

    public String loginformat = null;
    public String quitformat = null;
    public String kickFormat = null;
    public String worldFormat = null;
    public Boolean uselogin = null;
    public Boolean usequit = null;
    public Boolean useKick = null;
    public Boolean useWorld = null;
    public Boolean tellconsole = null;

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        loginformat = colorize(getConfig().getString("login-format"));
        quitformat = colorize(getConfig().getString("quit-format"));
        kickFormat = colorize(getConfig().getString("kick-format"));
        worldFormat = colorize(getConfig().getString("world-format"));
        usequit = getConfig().getBoolean("enable-quit-message");
        uselogin = getConfig().getBoolean("enable-login-message");
        tellconsole = getConfig().getBoolean("tell-console");
        useKick = getConfig().getBoolean("enable-kick-message");
        useWorld = getConfig().getBoolean("enable-world-message");
    }

    public String returnAlias(World w) {
        Plugin mv = getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (mv == null) return w.getName();
        MultiverseCore mvc = (MultiverseCore) mv;
        MultiverseWorld mvw = mvc.getMVWorldManager().getMVWorld(w);
        return mvw.getColoredWorldString();
    }

    public String colorize(String text) {
        if (text == null) return null;
        return text.replaceAll("(&([a-f0-9k-orR]))", "\u00A7$2");
    }

    Logger log = Logger.getLogger("Minecraft");

    public void onEnable() {

        version = this.getDescription().getVersion();

        Commands cmdExec = new Commands(this);

        getCommand("rmessages").setExecutor(cmdExec);

        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(playerListener, this);

        loadConfiguration();

        log.info("[RoyalMessages] RoyalMessages v" + version + " initiated.");

    }

    public void onDisable() {

        log.info("[RoyalMessages] RoyalMessages v" + version + " disabled.");

    }

}
