package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.utils.GUIUtils;
import com.rsscripting.serveradmin.utils.MenuLayoutUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.List;

public class MobEquipmentWorldMenu {

    private static final int WORLDS_PER_PAGE = 45;

    public static Inventory create(
            int page
    ) throws SQLException {

        RSMenuHolder holder =
                new RSMenuHolder(
                        "MOB_EQUIPMENT_WORLDS"
                );

        holder.setPage(
                page
        );

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        54,
                        "Mob Equipment Drops"
                );

        GUIUtils.fillEmptySlots(
                inventory
        );

        createBackButton(
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
                4,
                item
        );

    }

    private static void populateWorlds(
            Inventory inventory,
            int page
    ) throws SQLException {

        List<String> worlds =
                RSServerAdmin.getInstance()
                        .getWorldsDAO()
                        .getAllWorlds();

        int start =
                (page - 1) * WORLDS_PER_PAGE;

        int end =
                Math.min(
                        start + WORLDS_PER_PAGE,
                        worlds.size()
                );

        int worldCount =
                end - start;

        for (int i = start;
             i < end;
             i++) {

            String worldName =
                    worlds.get(i);

            ItemStack item =
                    new ItemStack(
                            Material.SKELETON_SKULL
                    );

            ItemMeta meta =
                    item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        "§e" + worldName
                );

                meta.getPersistentDataContainer()
                        .set(
                                new NamespacedKey(
                                        RSServerAdmin.getInstance(),
                                        "world_name"
                                ),
                                PersistentDataType.STRING,
                                worldName
                        );

                meta.setLore(
                        List.of(
                                "§7Configure equipment drops"
                        )
                );

                item.setItemMeta(
                        meta
                );

            }

            int relativeIndex =
                    i - start;

            int row =
                    relativeIndex / 9;

            int indexInRow =
                    relativeIndex % 9;

            int itemsRemaining =
                    worldCount - (row * 9);

            boolean lastPartialRow =
                    itemsRemaining < 9;

            int slot;

            if (lastPartialRow) {

                List<Integer> rowSlots =
                        MenuLayoutUtils.getCenteredSlots(
                                itemsRemaining
                        );

                slot =
                        9
                                + (row * 9)
                                + rowSlots.get(
                                indexInRow
                        );

            } else {

                slot =
                        9
                                + (row * 9)
                                + indexInRow;

            }

            inventory.setItem(
                    slot,
                    item
            );

        }

    }

}