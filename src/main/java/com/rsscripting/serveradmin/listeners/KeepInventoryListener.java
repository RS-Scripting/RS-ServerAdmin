package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.dao.KeepInventoryDAO;
import com.rsscripting.serveradmin.gui.KeepInventoryMenu;
import com.rsscripting.serveradmin.gui.MainMenu;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.settings.SettingKey;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class KeepInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!(event.getInventory().getHolder()
                instanceof RSMenuHolder holder)) {

            return;

        }

        if (!holder.getMenuId().equals(
                "KEEP_INVENTORY"
        )) {

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

        if (!(event.getWhoClicked()
                instanceof Player player)) {

            return;

        }

        switch (event.getSlot()) {

            case 3 -> player.openInventory(
                    MainMenu.create()
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

}