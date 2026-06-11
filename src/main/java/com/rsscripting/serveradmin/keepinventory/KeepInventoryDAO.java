package com.rsscripting.serveradmin.keepinventory;

import com.rsscripting.serveradmin.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeepInventoryDAO {

    private final DatabaseManager databaseManager;

    public KeepInventoryDAO(
            DatabaseManager databaseManager
    ) {

        this.databaseManager = databaseManager;

    }

    public boolean getWorldSetting(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT enabled
                             FROM keep_inventory
                             WHERE world_name = ?
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(
                            "enabled"
                    ) == 1;

                }

            }

        }

        return false;

    }

    public void setWorldSetting(
            String worldName,
            boolean enabled
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             INSERT OR REPLACE INTO keep_inventory
                             (
                                 world_name,
                                 enabled
                             )
                             VALUES
                             (
                                 ?,
                                 ?
                             )
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.setInt(
                    2,
                    enabled ? 1 : 0
            );

            statement.executeUpdate();

        }

    }

    public void ensureWorldExists(
            String worldName,
            boolean defaultValue
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             INSERT OR IGNORE INTO keep_inventory
                             (
                                 world_name,
                                 enabled
                             )
                             VALUES
                             (
                                 ?,
                                 ?
                             )
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.setInt(
                    2,
                    defaultValue ? 1 : 0
            );

            statement.executeUpdate();

        }

    }

    public java.util.List<String> getAllWorlds()
            throws SQLException {

        java.util.List<String> worlds =
                new java.util.ArrayList<>();

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT world_name
                             FROM keep_inventory
                             ORDER BY world_name
                             """
                     );

             ResultSet rs =
                     statement.executeQuery()) {

            while (rs.next()) {

                worlds.add(
                        rs.getString(
                                "world_name"
                        )
                );

            }

        }

        return worlds;

    }

}