package org.royaldev.royalmessages;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;


import java.util.logging.Logger;

public class PListener implements Listener {

    rMessages plugin;

    Logger log = Logger.getLogger("Minecraft");

    public PListener(rMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent e) {
        if (!plugin.useKick) {
            e.setLeaveMessage("");
            return;
        }
        String format = plugin.kickFormat;

        format = format.replaceAll("(&([a-f0-9]))", "\u00A7$2");

        String name = e.getPlayer().getName();
        String dispname = e.getPlayer().getDisplayName();
        String world = plugin.returnAlias(e.getPlayer().getWorld());
        int health = e.getPlayer().getHealth();
        String healthtext = null;

        if (format.contains("{healthtext}")) {
            if (health == 20) {
                healthtext = "full health";
            } else if (health < 20 && health > 15) {
                healthtext = "high health";
            } else if (health <= 15 && health > 10) {
                healthtext = "medium health";
            } else if (health <= 10 && health > 0) {
                healthtext = "low health";
            } else if (health == 0) {
                healthtext = "no health";
            }
            format = format.replace("{healthtext}", healthtext);
        }

        format = format.replace("{health}", Integer.toString(health));
        format = format.replace("{name}", name);
        format = format.replace("{dispname}", dispname);
        format = format.replace("{world}", world);

        e.setLeaveMessage(format);

        if (plugin.tellconsole) {
            log.info("[RoyalMessages] Kick: " + format);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getFrom().getWorld().equals(e.getTo().getWorld())) return;
        World to = e.getTo().getWorld();
        String message = plugin.worldFormat;
        Player p = e.getPlayer();
        message = message.replace("{name}", p.getName());
        message = message.replace("{dispname}", p.getDisplayName());
        message = message.replace("{world}", plugin.returnAlias(to));
        message = message.replace("{fromworld}", plugin.returnAlias(e.getFrom().getWorld()));
        plugin.getServer().broadcastMessage(message);
        if (plugin.tellconsole) log.info("[RoyalMessages] World join: " + message);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.uselogin) {
            event.setJoinMessage("");
            return;
        }
        String format = plugin.loginformat;

        format = format.replaceAll("(&([a-f0-9]))", "\u00A7$2");

        String name = event.getPlayer().getName();
        String dispname = event.getPlayer().getDisplayName();
        String world = event.getPlayer().getWorld().getName();
        int health = event.getPlayer().getHealth();
        String healthtext = null;

        if (format.contains("{healthtext}")) {
            if (health == 20) healthtext = "full health";
            else if (health < 20 && health > 15)
                healthtext = "high health";
            else if (health <= 15 && health > 10)
                healthtext = "medium health";
            else if (health <= 10 && health > 0)
                healthtext = "low health";
            else if (health == 0)
                healthtext = "no health";
            format = format.replace("{healthtext}", healthtext);
        }

        format = format.replace("{health}", Integer.toString(health));
        format = format.replace("{name}", name);
        format = format.replace("{dispname}", dispname);
        format = format.replace("{world}", world);

        event.setJoinMessage(format);

        if (plugin.tellconsole) {
            log.info("[RoyalMessages] Join: " + format);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.usequit) {
            event.setQuitMessage("");
            return;
        }
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
            log.info("[RoyalMessages] Quit: " + format);
        }
    }

}
