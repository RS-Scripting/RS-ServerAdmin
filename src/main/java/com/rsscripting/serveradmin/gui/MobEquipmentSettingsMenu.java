package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.utils.GUIUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobEquipmentSettingsMenu {

    public static Inventory create(
            String worldName
    ) {

        RSMenuHolder holder =
                new RSMenuHolder(
                        "MOB_EQUIPMENT_SETTINGS"
                );

        holder.setWorldName(
                worldName
        );

        boolean helmetEnabled = true;
        boolean chestplateEnabled = true;
        boolean leggingsEnabled = true;
        boolean bootsEnabled = true;
        boolean mainHandEnabled = true;
        boolean offHandEnabled = true;

        try {

            helmetEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getHelmet(
                                    worldName
                            );

            chestplateEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getChestplate(
                                    worldName
                            );

            leggingsEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getLeggings(
                                    worldName
                            );

            bootsEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getBoots(
                                    worldName
                            );

            mainHandEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getMainHand(
                                    worldName
                            );

            offHandEnabled =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO()
                            .getOffHand(
                                    worldName
                            );

        } catch (Exception ex) {

            RSServerAdmin.getInstance()
                    .getLogger()
                    .warning(
                            "Failed to load equipment drop settings for "
                                    + worldName
                                    + ": "
                                    + ex.getMessage()
                    );

        }

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        54,
                        "Equipment: " + worldName
                );

        GUIUtils.fillEmptySlots(
                inventory
        );

        /*
        | Back
         */

        ItemStack back =
                new ItemStack(
                        Material.ARROW
                );

        ItemMeta backMeta =
                back.getItemMeta();

        if (backMeta != null) {

            backMeta.setDisplayName(
                    "§cBack"
            );

            back.setItemMeta(
                    backMeta
            );

        }

        inventory.setItem(
                4,
                back
        );

        /*
        | Helmet
         */

        ItemStack helmet =
                new ItemStack(
                        Material.LEATHER_HELMET
                );

        if (helmetEnabled) {

            GUIUtils.addGlow(
                    helmet
            );

        }

        inventory.setItem(
                13,
                helmet
        );

        /*
        | Off Hand
         */

        ItemStack offHand =
                new ItemStack(
                        Material.SHIELD
                );

        if (offHandEnabled) {

            GUIUtils.addGlow(
                    offHand
            );

        }

        inventory.setItem(
                20,
                offHand
        );

        /*
        | Chestplate
         */

        ItemStack chestplate =
                new ItemStack(
                        Material.LEATHER_CHESTPLATE
                );

        if (chestplateEnabled) {

            GUIUtils.addGlow(
                    chestplate
            );

        }

        inventory.setItem(
                21,
                chestplate
        );

        /*
        | Main Hand
         */

        ItemStack mainHand =
                new ItemStack(
                        Material.IRON_SWORD
                );

        if (mainHandEnabled) {

            GUIUtils.addGlow(
                    mainHand
            );

        }

        inventory.setItem(
                22,
                mainHand
        );

        /*
        | Leggings
         */

        ItemStack leggings =
                new ItemStack(
                        Material.LEATHER_LEGGINGS
                );

        if (leggingsEnabled) {

            GUIUtils.addGlow(
                    leggings
            );

        }

        inventory.setItem(
                29,
                leggings
        );

        /*
        | Boots
         */

        ItemStack boots =
                new ItemStack(
                        Material.LEATHER_BOOTS
                );

        if (bootsEnabled) {

            GUIUtils.addGlow(
                    boots
            );

        }

        inventory.setItem(
                37,
                boots
        );

        return inventory;

    }

}