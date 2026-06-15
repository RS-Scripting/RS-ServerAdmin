package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobEquipmentMenu {

    public static Inventory create() {

        RSMenuHolder holder =
                new RSMenuHolder(
                        "MOB_EQUIPMENT"
                );

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        27,
                        "Mob Equipment Drops"
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
        | Armor Drops
         */

        ItemStack armor =
                new ItemStack(
                        Material.DIAMOND_CHESTPLATE
                );

        ItemMeta armorMeta =
                armor.getItemMeta();

        if (armorMeta != null) {

            armorMeta.setDisplayName(
                    "§aArmor Drops"
            );

            armor.setItemMeta(
                    armorMeta
            );

        }

        inventory.setItem(
                11,
                armor
        );

        /*
        | Hand Item Drops
         */

        ItemStack hands =
                new ItemStack(
                        Material.DIAMOND_SWORD
                );

        ItemMeta handMeta =
                hands.getItemMeta();

        if (handMeta != null) {

            handMeta.setDisplayName(
                    "§aHand Item Drops"
            );

            hands.setItemMeta(
                    handMeta
            );

        }

        inventory.setItem(
                15,
                hands
        );

        return inventory;

    }

}