package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.gui.GUIUtils;
import com.rsscripting.serveradmin.gui.RSMenuHolder;
import com.rsscripting.serveradmin.gui.KeepInventoryMenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu {

    public static Inventory create() {

        RSMenuHolder holder =
                new RSMenuHolder("main");

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        27,
                        "Main Menu"
                );

        ItemStack creeper = new ItemStack(Material.CREEPER_HEAD);

        ItemMeta creeperMeta = creeper.getItemMeta();

        if (creeperMeta != null) {
            creeperMeta.setDisplayName("§aCreepers");
            creeper.setItemMeta(creeperMeta);
        }

        ItemStack scanWorlds =
                new ItemStack(
                        Material.COMPASS
                );

        ItemMeta scanMeta =
                scanWorlds.getItemMeta();

        if (scanMeta != null) {
            scanMeta.setDisplayName("§aRescan Worlds");

            scanMeta.setLore(java.util.List.of(
                    "§7Rescan for new worlds",
                    "",
                    "§eClick to rescan."
            ));

            scanWorlds.setItemMeta(scanMeta);
        }

        inventory.setItem(4, scanWorlds);

        inventory.setItem(11, creeper);

        ItemStack keepInventory =
                new ItemStack(Material.CHEST);

        ItemMeta keepMeta =
                keepInventory.getItemMeta();

        if (keepMeta != null) {
            keepMeta.setDisplayName("§aKeep Inventory");
            keepInventory.setItemMeta(keepMeta);
        }

        inventory.setItem(15, keepInventory);

        GUIUtils.fillEmptySlots(inventory);

        return inventory;

    }

}