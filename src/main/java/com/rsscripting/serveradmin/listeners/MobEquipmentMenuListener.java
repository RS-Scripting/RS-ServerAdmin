package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.dao.MobEquipmentDropSettingsDAO;
import com.rsscripting.serveradmin.gui.MainMenu;
import com.rsscripting.serveradmin.gui.MobEquipmentSettingsMenu;
import com.rsscripting.serveradmin.gui.MobEquipmentWorldMenu;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MobEquipmentMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!(event.getInventory().getHolder()
                instanceof RSMenuHolder holder)) {

            return;

        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory()
                != event.getView().getTopInventory()) {

            return;

        }

        event.setCancelled(
                true
        );

        switch (holder.getMenuId()) {

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

                String worldName =
                        holder.getWorldName();

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

                    case 22 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setHelmet(
                                    worldName,
                                    !dao.getHelmet(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle helmet: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 30 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setOffHand(
                                    worldName,
                                    !dao.getOffHand(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle offhand: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 31 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setChestplate(
                                    worldName,
                                    !dao.getChestplate(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle chestplate: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 32 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setMainHand(
                                    worldName,
                                    !dao.getMainHand(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle main hand: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 40 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setLeggings(
                                    worldName,
                                    !dao.getLeggings(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle leggings: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 49 -> {

                        try {

                            MobEquipmentDropSettingsDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getEquipmentDropSettingsDAO();

                            dao.setBoots(
                                    worldName,
                                    !dao.getBoots(
                                            worldName
                                    )
                            );

                            player.openInventory(
                                    MobEquipmentSettingsMenu.create(
                                            worldName
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle boots: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

        }

    }

}