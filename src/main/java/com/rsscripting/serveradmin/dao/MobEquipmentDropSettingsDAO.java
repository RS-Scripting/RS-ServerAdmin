package com.rsscripting.serveradmin.dao;

import com.rsscripting.serveradmin.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MobEquipmentDropSettingsDAO {

    private final DatabaseManager databaseManager;

    public MobEquipmentDropSettingsDAO(
            DatabaseManager databaseManager
    ) {

        this.databaseManager =
                databaseManager;

    }

    public void ensureWorldExists(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             INSERT OR IGNORE INTO equipment_drop_settings (
                                 world_name,
                                 helmet,
                                 chestplate,
                                 leggings,
                                 boots,
                                 main_hand,
                                 off_hand
                             )
                             VALUES (?, 1, 1, 1, 1, 1, 1)
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

    }

    public boolean getHelmet(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "helmet"
        );

    }

    public void setHelmet(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "helmet",
                enabled
        );

    }

    public boolean getChestplate(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "chestplate"
        );

    }

    public void setChestplate(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "chestplate",
                enabled
        );

    }

    public boolean getLeggings(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "leggings"
        );

    }

    public void setLeggings(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "leggings",
                enabled
        );

    }

    public boolean getBoots(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "boots"
        );

    }

    public void setBoots(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "boots",
                enabled
        );

    }

    public boolean getMainHand(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "main_hand"
        );

    }

    public void setMainHand(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "main_hand",
                enabled
        );

    }

    public boolean getOffHand(
            String worldName
    ) throws SQLException {

        return getValue(
                worldName,
                "off_hand"
        );

    }

    public void setOffHand(
            String worldName,
            boolean enabled
    ) throws SQLException {

        setValue(
                worldName,
                "off_hand",
                enabled
        );

    }

    private boolean getValue(
            String worldName,
            String column
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT " + column +
                                     " FROM equipment_drop_settings " +
                                     "WHERE world_name = ?"
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    return result.getBoolean(
                            column
                    );

                }

            }

        }

        return true;

    }

    private void setValue(
            String worldName,
            String column,
            boolean enabled
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "UPDATE equipment_drop_settings " +
                                     "SET " + column +
                                     " = ? WHERE world_name = ?"
                     )) {

            statement.setBoolean(
                    1,
                    enabled
            );

            statement.setString(
                    2,
                    worldName
            );

            statement.executeUpdate();

        }

    }

}