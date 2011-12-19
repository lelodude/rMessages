package org.royaldev.royalmessages;

import java.util.logging.Logger;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PListener extends PlayerListener {

	RoyalMessages plugin;

	Logger log = Logger.getLogger("Minecraft");

	public PListener(RoyalMessages plugin) {
		this.plugin = plugin;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!plugin.uselogin) {
			event.setJoinMessage("");
		} else {
			String format = plugin.loginformat;

			format = format.replaceAll("(&([a-f0-9]))", "\u00A7$2");

			String name = event.getPlayer().getName();
			String dispname = event.getPlayer().getDisplayName();
			String world = event.getPlayer().getWorld().getName();

			format = format.replace("{name}", name);
			format = format.replace("{dispname}", dispname);
			format = format.replace("{world}", world);

			event.setJoinMessage(format);

			if (plugin.tellconsole) {
				log.info("[RoyalMessages] " + format);
			}
		}
	}

	public void onPlayerQuit(PlayerQuitEvent event) {
		if (!plugin.usequit) {
			event.setQuitMessage("");
		} else {
			String format = plugin.quitformat;

			format = format.replaceAll("(&([a-f0-9]))", "\u00A7$2");

			String name = event.getPlayer().getName();
			String dispname = event.getPlayer().getDisplayName();
			String world = event.getPlayer().getWorld().getName();

			format = format.replace("{name}", name);
			format = format.replace("{dispname}", dispname);
			format = format.replace("{world}", world);

			event.setQuitMessage(format);

			if (plugin.tellconsole) {
				log.info("[RoyalMessages] " + format);
			}
		}
	}

}
