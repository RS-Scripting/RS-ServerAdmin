package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.GameRule;

public class WorldLoadListener implements Listener {

    @EventHandler
    public void onWorldLoad(
            WorldLoadEvent event
    ) {

        try {

            RSServerAdmin plugin =
                    RSServerAdmin.getInstance();

            plugin.getWorldsDAO()
                    .ensureWorldExists(
                            event.getWorld().getName()
                    );

            boolean keepInventoryDefault =
                    plugin.getSettingsDAO()
                            .getDefault(
                                    SettingKey.KEEP_INVENTORY_NEW_WORLD
                            );

            plugin.getKeepInventoryDAO()
                    .ensureWorldExists(
                            event.getWorld().getName(),
                            keepInventoryDefault
                    );

            boolean blockDamageDefault =
                    plugin.getSettingsDAO()
                            .getDefault(
                                    SettingKey.CREEPER_BLOCK_DAMAGE
                            );

            boolean entityDamageDefault =
                    plugin.getSettingsDAO()
                            .getDefault(
                                    SettingKey.CREEPER_ENTITY_DAMAGE
                            );

            plugin.getCreeperSettingsDAO()
                    .ensureWorldExists(
                            event.getWorld().getName(),
                            blockDamageDefault,
                            entityDamageDefault
                    );

            plugin.getEquipmentDropSettingsDAO()
                    .ensureWorldExists(
                            event.getWorld().getName()
                    );

            boolean keepInventoryEnabled =
                    plugin.getKeepInventoryDAO()
                            .getWorldSetting(
                                    event.getWorld().getName()
                            );

            event.getWorld().setGameRule(
                    GameRule.KEEP_INVENTORY,
                    keepInventoryEnabled
            );

        } catch (Exception ex) {

            RSServerAdmin.getInstance()
                    .getLogger()
                    .warning(
                            "Failed to register world settings for "
                                    + event.getWorld().getName()
                                    + ": "
                                    + ex.getMessage()
                    );

        }

    }

}