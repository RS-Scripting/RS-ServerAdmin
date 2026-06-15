package com.rsscripting.serveradmin.utils;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldSyncUtils {

    public static void synchronizeWorlds()
            throws Exception {

        RSServerAdmin plugin =
                RSServerAdmin.getInstance();

        boolean keepInventoryDefault =
                plugin.getSettingsDAO()
                        .getDefault(
                                SettingKey.KEEP_INVENTORY_NEW_WORLD
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

        Set<String> bukkitWorlds =
                new HashSet<>();

        /*
        | Add missing worlds
         */

        for (World world : Bukkit.getWorlds()) {

            String worldName =
                    world.getName();

            bukkitWorlds.add(
                    worldName
            );

            plugin.getWorldsDAO()
                    .ensureWorldExists(
                            worldName
                    );

            plugin.getKeepInventoryDAO()
                    .ensureWorldExists(
                            worldName,
                            keepInventoryDefault
                    );

            plugin.getCreeperSettingsDAO()
                    .ensureWorldExists(
                            worldName,
                            blockDamageDefault,
                            entityDamageDefault
                    );

            plugin.getEquipmentDropSettingsDAO()
                    .ensureWorldExists(
                            worldName
                    );

        }

        /*
        | Remove deleted worlds
         */

        List<String> databaseWorlds =
                plugin.getWorldsDAO()
                        .getAllWorlds();

        for (String worldName
                : databaseWorlds) {

            if (bukkitWorlds.contains(
                    worldName
            )) {

                continue;

            }

            plugin.getWorldsDAO()
                    .deleteWorldEverywhere(
                            worldName
                    );

            plugin.getLogger().info(
                    "Removed stale world settings for: "
                            + worldName
            );

        }

    }

}