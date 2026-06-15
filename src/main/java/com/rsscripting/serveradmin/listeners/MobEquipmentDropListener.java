package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.dao.MobEquipmentDropSettingsDAO;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class MobEquipmentDropListener implements Listener {

    @EventHandler
    public void onEntityDeath(
            EntityDeathEvent event
    ) {

        LivingEntity entity =
                event.getEntity();

        EntityEquipment equipment =
                entity.getEquipment();

        if (equipment == null) {
            return;
        }

        String worldName =
                entity.getWorld()
                        .getName();

        try {

            MobEquipmentDropSettingsDAO dao =
                    RSServerAdmin.getInstance()
                            .getEquipmentDropSettingsDAO();

            ItemStack helmet =
                    equipment.getHelmet();

            if (dao.getHelmet(worldName)
                    && helmet != null
                    && !helmet.getType().isAir()
                    && !event.getDrops().contains(helmet)) {

                event.getDrops().add(
                        helmet.clone()
                );

            }

            ItemStack chestplate =
                    equipment.getChestplate();

            if (dao.getChestplate(worldName)
                    && chestplate != null
                    && !chestplate.getType().isAir()
                    && !event.getDrops().contains(chestplate)) {

                event.getDrops().add(
                        chestplate.clone()
                );

            }

            ItemStack leggings =
                    equipment.getLeggings();

            if (dao.getLeggings(worldName)
                    && leggings != null
                    && !leggings.getType().isAir()
                    && !event.getDrops().contains(leggings)) {

                event.getDrops().add(
                        leggings.clone()
                );

            }

            ItemStack boots =
                    equipment.getBoots();

            if (dao.getBoots(worldName)
                    && boots != null
                    && !boots.getType().isAir()
                    && !event.getDrops().contains(boots)) {

                event.getDrops().add(
                        boots.clone()
                );

            }

            ItemStack mainHand =
                    equipment.getItemInMainHand();

            if (dao.getMainHand(worldName)
                    && mainHand != null
                    && !mainHand.getType().isAir()
                    && !event.getDrops().contains(mainHand)) {

                event.getDrops().add(
                        mainHand.clone()
                );

            }

            ItemStack offHand =
                    equipment.getItemInOffHand();

            if (dao.getOffHand(worldName)
                    && offHand != null
                    && !offHand.getType().isAir()
                    && !event.getDrops().contains(offHand)) {

                event.getDrops().add(
                        offHand.clone()
                );

            }

        } catch (Exception ex) {

            RSServerAdmin.getInstance()
                    .getLogger()
                    .warning(
                            "Failed to process equipment drops: "
                                    + ex.getMessage()
                    );

        }

    }

}