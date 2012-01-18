package org.royaldev.royalmessages;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PListener implements Listener {

    RoyalMessages plugin;

    Logger log = Logger.getLogger("Minecraft");

    public PListener(RoyalMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler(event = PlayerKickEvent.class, priority = EventPriority.NORMAL)
    public void onPlayerKick(PlayerKickEvent e) {
        if (!plugin.useKick) {
            e.setLeaveMessage("");
            return;
        }
        String format = plugin.kickFormat;

        format = format.replaceAll("(&([a-f0-9]))", "\u00A7$2");

        String name = e.getPlayer().getName();
        String dispname = e.getPlayer().getDisplayName();
        String world = e.getPlayer().getWorld().getName();
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

    @EventHandler(event = PlayerJoinEvent.class, priority = EventPriority.NORMAL)
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

        event.setJoinMessage(format);

        if (plugin.tellconsole) {
            log.info("[RoyalMessages] Join: " + format);
        }
    }

    @EventHandler(event = PlayerQuitEvent.class, priority = EventPriority.NORMAL)
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
                log.info("[RoyalMessages] Quit: " + format);
            }
        }
    }

}
