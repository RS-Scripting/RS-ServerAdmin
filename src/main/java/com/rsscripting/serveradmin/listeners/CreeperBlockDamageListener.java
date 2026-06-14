package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperBlockDamageListener implements Listener {

    @EventHandler
    public void onEntityExplode(
            EntityExplodeEvent event
    ) {

        if (!(event.getEntity() instanceof Creeper)) {
            return;
        }

        try {

            String worldName =
                    event.getLocation()
                            .getWorld()
                            .getName();

            boolean blockDamageEnabled =
                    RSServerAdmin.getInstance()
                            .getCreeperSettingsDAO()
                            .getBlockDamage(
                                    worldName
                            );

            if (blockDamageEnabled) {
                return;
            }

            event.blockList().clear();
            event.setYield(0F);

        } catch (Exception ex) {

            RSServerAdmin.getInstance()
                    .getLogger()
                    .warning(
                            "Failed to process Creeper block damage: "
                                    + ex.getMessage()
                    );

        }

    }

}