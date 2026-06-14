package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreeperEntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(
            EntityDamageByEntityEvent event
    ) {

        if (!(event.getDamager() instanceof Creeper)) {
            return;
        }

        try {

            String worldName =
                    event.getEntity()
                            .getWorld()
                            .getName();

            boolean entityDamageEnabled =
                    RSServerAdmin.getInstance()
                            .getCreeperSettingsDAO()
                            .getEntityDamage(
                                    worldName
                            );

            if (entityDamageEnabled) {
                return;
            }

            event.setCancelled(
                    true
            );

        } catch (Exception ex) {

            RSServerAdmin.getInstance()
                    .getLogger()
                    .warning(
                            "Failed to process Creeper entity damage: "
                                    + ex.getMessage()
                    );

        }

    }

}