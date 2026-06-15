package com.rsscripting.serveradmin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class GUIUtils {

    private GUIUtils() {
    }

    public static ItemStack createFiller() {

        ItemStack item =
                new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(" ");
            item.setItemMeta(meta);
        }

        return item;
    }

    public static void fillEmptySlots(Inventory inventory) {

        ItemStack filler = createFiller();

        for (int slot = 0; slot < inventory.getSize(); slot++) {

            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, filler);
            }

        }

    }

    public static void addGlow(ItemStack item) {

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.addEnchant(
                Enchantment.UNBREAKING,
                1,
                true
        );

        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS
        );

        item.setItemMeta(meta);

    }

}