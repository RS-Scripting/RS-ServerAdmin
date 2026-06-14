package com.rsscripting.serveradmin.gui;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreeperMenu {

    public static Inventory create() {

        RSMenuHolder holder =
                new RSMenuHolder("creeper");

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        27,
                        "Creeper Settings"
                );

        try {

            try {

                boolean disableBlockDamage =
                        RSServerAdmin.getInstance()
                                .getSettingsDAO()
                                .getDefault(
                                        SettingKey.CREEPER_BLOCK_DAMAGE
                                );

                ItemStack blockDamage =
                        new ItemStack(Material.TNT);

                ItemMeta blockMeta =
                        blockDamage.getItemMeta();

                if (blockMeta != null) {

                    blockMeta.setDisplayName(
                            "§aBlock Damage"
                    );

                    blockMeta.setLore(
                            java.util.List.of(
                                    "§7Disable Creeper damage to blocks",
                                    "",
                                    disableBlockDamage
                                            ? "§aCurrent: Explosion will not damage blocks"
                                            : "§cCurrent: Explosion will damage blocks"
                            )
                    );

                    blockDamage.setItemMeta(
                            blockMeta
                    );

                }

                inventory.setItem(
                        11,
                        blockDamage
                );

                boolean disableEntityDamage =
                        RSServerAdmin.getInstance()
                                .getSettingsDAO()
                                .getDefault(
                                        SettingKey.CREEPER_ENTITY_DAMAGE
                                );

                ItemStack entityDamage =
                        new ItemStack(
                                Material.TOTEM_OF_UNDYING
                        );

                ItemMeta entityMeta =
                        entityDamage.getItemMeta();

                if (entityMeta != null) {

                    entityMeta.setDisplayName(
                            "§aEntity Damage"
                    );

                    entityMeta.setLore(
                            java.util.List.of(
                                    "§7Disable Creeper damage to players and mobs",
                                    "",
                                    disableEntityDamage
                                            ? "§aCurrent: Explosion will not damage entities"
                                            : "§cCurrent: Explosion will damage entities"
                            )
                    );

                    entityDamage.setItemMeta(
                            entityMeta
                    );

                }

                inventory.setItem(
                        15,
                        entityDamage
                );

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        ItemStack back =
                new ItemStack(Material.ARROW);

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

        GUIUtils.fillEmptySlots(
                inventory
        );

        return inventory;

    }

}