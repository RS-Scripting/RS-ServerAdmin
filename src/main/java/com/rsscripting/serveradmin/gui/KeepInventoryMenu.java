package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.keepinventory.KeepInventoryDAO;
import com.rsscripting.serveradmin.gui.RSMenuHolder;
import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.List;

public class KeepInventoryMenu {

    private static final int WORLDS_PER_PAGE = 45;

    public static Inventory create(
            int page
    ) throws SQLException {

        RSMenuHolder holder =
                new RSMenuHolder(
                        "KEEP_INVENTORY"
                );

        holder.setPage(
                page
        );

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        54,
                        "Keep Inventory"
                );

        GUIUtils.fillEmptySlots(
                inventory
        );

        createBackButton(
                inventory
        );

        createDefaultButton(
                inventory
        );

        populateWorlds(
                inventory,
                page
        );

        return inventory;

    }

    private static void createBackButton(
            Inventory inventory
    ) {

        ItemStack item =
                new ItemStack(
                        Material.ARROW
                );

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(
                    "§cBack"
            );

            item.setItemMeta(
                    meta
            );

        }

        inventory.setItem(
                0,
                item
        );

    }

    private static void createDefaultButton(
            Inventory inventory
    ) throws SQLException {

        boolean enabled =
                RSServerAdmin.getInstance()
                        .getSettingsDAO()
                        .getDefault(
                                SettingKey.KEEP_INVENTORY_NEW_WORLD
                        );

        ItemStack item =
                new ItemStack(
                        Material.CHEST
                );

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(
                    "§eDefault For New Worlds"
            );

            meta.setLore(
                    List.of(
                            "§7Controls newly detected worlds",
                            "",
                            enabled
                                    ? "§aCurrent: New worlds keep inventory"
                                    : "§cCurrent: New worlds drop inventory"
                    )
            );

            item.setItemMeta(
                    meta
            );

        }

        if (enabled) {

            GUIUtils.addGlow(
                    item
            );

        }

        inventory.setItem(
                4,
                item
        );

    }

    private static void populateWorlds(
            Inventory inventory,
            int page
    ) throws SQLException {

        KeepInventoryDAO dao =
                RSServerAdmin.getInstance()
                        .getKeepInventoryDAO();

        List<String> worlds =
                dao.getAllWorlds();

        int start =
                page * WORLDS_PER_PAGE;

        int slot =
                9;

        for (int i = start;
             i < worlds.size() && slot < 54;
             i++) {

            String worldName =
                    worlds.get(i);

            boolean enabled =
                    dao.getWorldSetting(
                            worldName
                    );

            ItemStack item =
                    new ItemStack(
                            Material.CHEST
                    );

            ItemMeta meta =
                    item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        "§e" + worldName
                );

                meta.setLore(
                        List.of(
                                "§7Keep Inventory",
                                "",
                                enabled
                                        ? "§aCurrent: Players keep inventory on death"
                                        : "§cCurrent: Players drop inventory on death"
                        )
                );

                item.setItemMeta(
                        meta
                );

            }

            if (enabled) {

                GUIUtils.addGlow(
                        item
                );

            }

            inventory.setItem(
                    slot,
                    item
            );

            slot++;

        }

    }

}