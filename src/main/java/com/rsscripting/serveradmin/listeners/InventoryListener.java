package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.gui.*;
import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.settings.SettingKey;
import com.rsscripting.serveradmin.dao.KeepInventoryDAO;
import com.rsscripting.serveradmin.gui.MobEquipmentWorldMenu;
import com.rsscripting.serveradmin.gui.MobEquipmentSettingsMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getInventory().getHolder() instanceof RSMenuHolder holder)) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }

        event.setCancelled(true);

        switch (holder.getMenuId()) {

            case "main" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    /*
                    | Rescan for new worlds
                     */

                    case 4 -> {

                        try {

                            boolean keepInventoryDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.KEEP_INVENTORY_NEW_WORLD
                                            );

                            boolean blockDamageDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.CREEPER_BLOCK_DAMAGE
                                            );

                            boolean entityDamageDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.CREEPER_ENTITY_DAMAGE
                                            );

                            for (org.bukkit.World world
                                    : org.bukkit.Bukkit.getWorlds()) {

                                RSServerAdmin.getInstance()
                                        .getWorldsDAO()
                                        .ensureWorldExists(
                                                world.getName()
                                        );

                                RSServerAdmin.getInstance()
                                        .getKeepInventoryDAO()
                                        .ensureWorldExists(
                                                world.getName(),
                                                keepInventoryDefault
                                        );

                                RSServerAdmin.getInstance()
                                        .getCreeperSettingsDAO()
                                        .ensureWorldExists(
                                                world.getName(),
                                                blockDamageDefault,
                                                entityDamageDefault
                                        );

                                RSServerAdmin.getInstance()
                                        .getEquipmentDropSettingsDAO()
                                        .ensureWorldExists(
                                                world.getName()
                                        );

                            }

                            player.sendMessage(
                                    "§aWorld scan complete."
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "World scan failed: "
                                                    + ex.getMessage()
                                    );

                            player.sendMessage(
                                    "§cWorld scan failed."
                            );

                        }

                    }

                    /*
                    | Mob Inventory Drop
                     */

                    case 13 -> {

                        try {

                            player.openInventory(
                                    MobEquipmentWorldMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Mob Equipment menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    /*
                    | Creeper menu
                     */

                    case 11 -> player.openInventory(
                            CreeperMenu.create()
                    );

                    /*
                    | Keep Inventory Menu
                     */

                    case 15 -> {

                        try {

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Keep Inventory menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            /*
            |  Creeper menu
             */

            case "creeper" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 4 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                    case 11 -> {

                        try {

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper Block Damage menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 15 -> {

                        try {

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper Entity Damage menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            /*
            |  Keep Inventory
            */

            case "KEEP_INVENTORY" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.KEEP_INVENTORY_NEW_WORLD
                                    );

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Keep Inventory default: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            KeepInventoryDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getKeepInventoryDAO();

                            if (event.getCurrentItem() == null) {
                                return;
                            }

                            if (!event.getCurrentItem().hasItemMeta()) {
                                return;
                            }

                            ItemMeta meta =
                                    event.getCurrentItem()
                                            .getItemMeta();

                            if (meta == null) {
                                return;
                            }

                            String worldName =
                                    meta.getPersistentDataContainer()
                                            .get(
                                                    new NamespacedKey(
                                                            RSServerAdmin.getInstance(),
                                                            "world_name"
                                                    ),
                                                    PersistentDataType.STRING
                                            );

                            if (worldName == null
                                    || worldName.isBlank()) {

                                return;

                            }

                            boolean enabled =
                                    dao.getWorldSetting(
                                            worldName
                                    );

                            dao.setWorldSetting(
                                    worldName,
                                    !enabled
                            );

                            World world =
                                    Bukkit.getWorld(
                                            worldName
                                    );

                            if (world != null) {

                                world.setGameRule(
                                        GameRule.KEEP_INVENTORY,
                                        !enabled
                                );

                            }

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Keep Inventory world setting: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            case "CREEPER_BLOCK_DAMAGE" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> {

                        try {

                            player.openInventory(
                                    CreeperMenu.create()
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_BLOCK_DAMAGE
                                    );

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle Creeper Block Damage default: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            if (event.getCurrentItem() == null) {
                                return;
                            }

                            if (!event.getCurrentItem().hasItemMeta()) {
                                return;
                            }

                            ItemMeta meta =
                                    event.getCurrentItem()
                                            .getItemMeta();

                            if (meta == null) {
                                return;
                            }

                            String worldName =
                                    meta.getPersistentDataContainer()
                                            .get(
                                                    new NamespacedKey(
                                                            RSServerAdmin.getInstance(),
                                                            "world_name"
                                                    ),
                                                    PersistentDataType.STRING
                                            );

                            if (worldName == null
                                    || worldName.isBlank()) {

                                return;

                            }

                            boolean enabled =
                                    RSServerAdmin.getInstance()
                                            .getCreeperSettingsDAO()
                                            .getBlockDamage(
                                                    worldName
                                            );

                            RSServerAdmin.getInstance()
                                    .getCreeperSettingsDAO()
                                    .setBlockDamage(
                                            worldName,
                                            !enabled
                                    );

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Creeper Block Damage setting: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            case "CREEPER_ENTITY_DAMAGE" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> {

                        try {

                            player.openInventory(
                                    CreeperMenu.create()
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_ENTITY_DAMAGE
                                    );

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle Creeper Entity Damage default: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            if (event.getCurrentItem() == null) {
                                return;
                            }

                            if (!event.getCurrentItem().hasItemMeta()) {
                                return;
                            }

                            ItemMeta meta =
                                    event.getCurrentItem()
                                            .getItemMeta();

                            if (meta == null) {
                                return;
                            }

                            String worldName =
                                    meta.getPersistentDataContainer()
                                            .get(
                                                    new NamespacedKey(
                                                            RSServerAdmin.getInstance(),
                                                            "world_name"
                                                    ),
                                                    PersistentDataType.STRING
                                            );

                            if (worldName == null
                                    || worldName.isBlank()) {

                                return;

                            }

                            boolean enabled =
                                    RSServerAdmin.getInstance()
                                            .getCreeperSettingsDAO()
                                            .getEntityDamage(
                                                    worldName
                                            );

                            RSServerAdmin.getInstance()
                                    .getCreeperSettingsDAO()
                                    .setEntityDamage(
                                            worldName,
                                            !enabled
                                    );

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Creeper Entity Damage setting: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            case "MOB_EQUIPMENT" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 4 -> player.openInventory(
                            MainMenu.create()
                    );

                }

            }

            case "MOB_EQUIPMENT_WORLDS" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 4 -> {

                        player.openInventory(
                                MainMenu.create()
                        );

                    }

                    default -> {

                        if (event.getCurrentItem() == null) {
                            return;
                        }

                        if (!event.getCurrentItem().hasItemMeta()) {
                            return;
                        }

                        ItemMeta meta =
                                event.getCurrentItem()
                                        .getItemMeta();

                        if (meta == null) {
                            return;
                        }

                        String worldName =
                                meta.getPersistentDataContainer()
                                        .get(
                                                new NamespacedKey(
                                                        RSServerAdmin.getInstance(),
                                                        "world_name"
                                                ),
                                                PersistentDataType.STRING
                                        );

                        if (worldName == null
                                || worldName.isBlank()) {

                            return;

                        }

                        player.openInventory(
                                MobEquipmentSettingsMenu.create(
                                        worldName
                                )
                        );

                    }

                }

            }

            case "MOB_EQUIPMENT_SETTINGS" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 4 -> {

                        try {

                            player.openInventory(
                                    MobEquipmentWorldMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Mob Equipment world menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

        }

    }

}