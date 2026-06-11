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

            boolean disableEntityDamage =
                    RSServerAdmin.getInstance()
                            .getSettingsDAO()
                            .getDefault(
                                    SettingKey.CREEPER_ENTITY_DAMAGE
                            );

            if (!disableEntityDamage) {
                return;
            }

            event.setCancelled(true);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}