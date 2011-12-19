package org.royaldev.royalmessages;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RoyalMessages extends JavaPlugin {

	public String version = "0.0.1";

	private final PListener playerListener = new PListener(this);

	public String loginformat = null;
	public String quitformat = null;
	public Boolean uselogin = null;
	public Boolean usequit = null;
	public Boolean tellconsole = null;

	public void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		loginformat = this.getConfig().getString("login-format")
				.replaceAll("(&([a-f0-9]))", "\u00A7$2");
		quitformat = this.getConfig().getString("quit-format")
				.replaceAll("(&([a-f0-9]))", "\u00A7$2");
		usequit = this.getConfig().getBoolean("enable-quit-message");
		uselogin = this.getConfig().getBoolean("enable-login-message");
		tellconsole = this.getConfig().getBoolean("tell-console");
	}

	protected FileConfiguration config;

	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {

		Commands cmdExec = new Commands(this);

		getCommand("rmessages").setExecutor(cmdExec);

		PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener,
				Event.Priority.Normal, this);

		loadConfiguration();

		log.info("[RoyalMessages] RoyalMessages v" + version + " initiated.");

	}

	public void onDisable() {

		log.info("[RoyalMessages] RoyalMessages v" + version + "disabled.");

	}

}
