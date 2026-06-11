package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.gui.KeepInventoryMenu;
import com.rsscripting.serveradmin.gui.RSMenuHolder;
import com.rsscripting.serveradmin.gui.CreeperMenu;
import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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

                    case 11 -> player.openInventory(
                            CreeperMenu.create()
                    );

                    case 15 -> {

                        try {

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                    }

                }

            }

            case "creeper" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 11 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_BLOCK_DAMAGE
                                    );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                        player.openInventory(
                                CreeperMenu.create()
                        );

                    }

                    case 15 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_ENTITY_DAMAGE
                                    );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                        player.openInventory(
                                CreeperMenu.create()
                        );

                    }

                    case 22 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                }

            }

        }

    }

}