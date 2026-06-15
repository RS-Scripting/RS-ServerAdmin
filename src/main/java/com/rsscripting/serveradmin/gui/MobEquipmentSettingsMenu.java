package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.utils.GUIUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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
                        "Mob Drops: " + worldName
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

        ItemMeta helmetMeta =
                helmet.getItemMeta();

        helmetMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );

        if (helmetMeta != null) {

            helmetMeta.setDisplayName(
                    "§eHelmet"
            );

            helmetMeta.setLore(
                    java.util.List.of(
                            helmetEnabled
                                    ? "§aCurrent: Mobs will drop helmet if equipped."
                                    : "§cCurrent: Mobs will not drop helmet if equipped.",
                            "",
                            helmetEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            helmet.setItemMeta(
                    helmetMeta
            );

        }

        if (helmetEnabled) {

            GUIUtils.addGlow(
                    helmet
            );

        }

        inventory.setItem(
                22,
                helmet
        );

        /*
        | Off Hand
         */

        ItemStack offHand =
                new ItemStack(
                        Material.SHIELD
                );

        ItemMeta offHandMeta =
                offHand.getItemMeta();

        offHandMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );

        if (offHandMeta != null) {

            offHandMeta.setDisplayName(
                    "§eOff Hand"
            );

            offHandMeta.setLore(
                    java.util.List.of(
                            offHandEnabled
                                    ? "§aCurrent: Mobs will drop held item."
                                    : "§cCurrent: Mobs will not drop held item.",
                            "",
                            offHandEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            offHand.setItemMeta(
                    offHandMeta
            );

        }

        if (offHandEnabled) {

            GUIUtils.addGlow(
                   offHand
            );

        }

        inventory.setItem(
                30,
                offHand
        );

        /*
        | Chestplate
         */

        ItemStack chestplate =
                new ItemStack(
                        Material.LEATHER_CHESTPLATE
                );

        ItemMeta chestplateMeta =
                chestplate.getItemMeta();

        chestplateMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );

        if (chestplateMeta != null) {

            chestplateMeta.setDisplayName(
                    "§eChestplate"
            );

            chestplateMeta.setLore(
                    java.util.List.of(
                            chestplateEnabled
                                    ? "§aCurrent: Mobs will drop chestplate if equipped."
                                    : "§cCurrent: Mobs will not drop chestplate if equipped.",
                            "",
                            chestplateEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            chestplate.setItemMeta(
                    chestplateMeta
            );

        }

        if (chestplateEnabled) {

            GUIUtils.addGlow(
                    chestplate
            );

        }

        inventory.setItem(
                31,
                chestplate
        );

        /*
        | Main Hand
         */

        ItemStack mainHand =
                new ItemStack(
                        Material.IRON_SWORD
                );

        ItemMeta mainHandMeta =
                mainHand.getItemMeta();

        mainHandMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );


        if (mainHandMeta != null) {

            mainHandMeta.setDisplayName(
                    "§eMain Hand"
            );

            mainHandMeta.setLore(
                    java.util.List.of(
                            mainHandEnabled
                                    ? "§aCurrent: Mobs will drop held item if equipped."
                                    : "§cCurrent: Mobs will not drop held item if equipped.",
                            "",
                            mainHandEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            mainHand.setItemMeta(
                    mainHandMeta
            );

        }

        if (mainHandEnabled) {

            GUIUtils.addGlow(
                    mainHand
            );

        }

        inventory.setItem(
                32,
                mainHand
        );

        /*
        | Leggings
         */

        ItemStack leggings =
                new ItemStack(
                        Material.LEATHER_LEGGINGS
                );

        ItemMeta leggingsMeta =
                leggings.getItemMeta();

        leggingsMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );


        if (mainHandMeta != null) {

            leggingsMeta.setDisplayName(
                    "§eLeggings"
            );

            leggingsMeta.setLore(
                    java.util.List.of(
                            leggingsEnabled
                                    ? "§aCurrent: Mobs will drop leggings if equipped."
                                    : "§cCurrent: Mobs will not drop leggings if equipped.",
                            "",
                            leggingsEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            leggings.setItemMeta(
                    leggingsMeta
            );

        }

        if (leggingsEnabled) {

            GUIUtils.addGlow(
                    leggings
            );

        }

        inventory.setItem(
                40,
                leggings
        );

        /*
        | Boots
         */

        ItemStack boots =
                new ItemStack(
                        Material.LEATHER_BOOTS
                );

        ItemMeta bootsMeta =
                boots.getItemMeta();

        bootsMeta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );


        if (bootsMeta != null) {

            bootsMeta.setDisplayName(
                    "§eBoots"
            );

            bootsMeta.setLore(
                    java.util.List.of(
                            bootsEnabled
                                    ? "§aCurrent: Mobs will drop boots if equipped."
                                    : "§cCurrent: Mobs will not drop boots if equipped.",
                            "",
                            bootsEnabled
                                    ? "§7Click to Disable"
                                    : "§7Click to Enable"
                    )
            );

            boots.setItemMeta(
                    bootsMeta
            );

        }

        if (bootsEnabled) {

            GUIUtils.addGlow(
                    boots
            );

        }

        inventory.setItem(
                49,
                boots
        );

        return inventory;

    }

}