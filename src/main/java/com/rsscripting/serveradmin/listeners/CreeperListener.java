package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperListener implements Listener {

    @EventHandler
    public void onEntityExplode(
            EntityExplodeEvent event
    ) {

        if (!(event.getEntity() instanceof Creeper)) {
            return;
        }

        try {

            boolean disableBlockDamage =
                    RSServerAdmin.getInstance()
                            .getSettingsDAO()
                            .getDefault(
                                    SettingKey.CREEPER_BLOCK_DAMAGE
                            );

            if (!disableBlockDamage) {
                return;
            }

            event.blockList().clear();
            event.setYield(0F);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}