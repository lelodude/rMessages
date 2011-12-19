package org.royaldev.royalmessages;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	RoyalMessages plugin;

	public Commands(RoyalMessages plugin) {
		this.plugin = plugin;
	}

	public boolean isAuthorized(final CommandSender player, final String node) {
		if (player.isOp()) {
			return true;
		} else if (player.hasPermission(node)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rmessages")) {
			if (!isAuthorized(sender, "rmessages.rmessages")) {
				sender.sendMessage(ChatColor.RED
						+ "You don't have permission for that!");
				return true;
			} else {
				plugin.reloadConfig();
				plugin.loginformat = plugin.getConfig()
						.getString("login-format")
						.replaceAll("(&([a-f0-9]))", "\u00A7$2");
				plugin.quitformat = plugin.getConfig().getString("quit-format")
						.replaceAll("(&([a-f0-9]))", "\u00A7$2");
				plugin.usequit = plugin.getConfig().getBoolean(
						"enable-quit-message");
				plugin.uselogin = plugin.getConfig().getBoolean(
						"enable-login-message");
				plugin.tellconsole = plugin.getConfig().getBoolean(
						"tell-console");
				sender.sendMessage(ChatColor.AQUA + "RoyalMessages"
						+ ChatColor.DARK_GREEN + " v" + plugin.version
						+ " reloaded.");
				return true;
			}
		}
		return true;
	}

}
