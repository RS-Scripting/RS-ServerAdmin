package com.rsscripting.serveradmin.dao;

import com.rsscripting.serveradmin.database.DatabaseManager;
import com.rsscripting.serveradmin.RSServerAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorldsDAO {

    private final DatabaseManager databaseManager;

    public WorldsDAO(
            DatabaseManager databaseManager
    ) {

        this.databaseManager = databaseManager;

    }

    public void ensureWorldExists(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             INSERT OR IGNORE INTO worlds
                             (
                                 world_name
                             )
                             VALUES
                             (
                                 ?
                             )
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

    }

    public List<String> getAllWorlds()
            throws SQLException {

        List<String> worlds =
                new ArrayList<>();

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT world_name
                             FROM worlds
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

    public void deleteWorldEverywhere(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             DELETE FROM worlds
                             WHERE world_name = ?
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             DELETE FROM keep_inventory
                             WHERE world_name = ?
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             DELETE FROM creeper_settings
                             WHERE world_name = ?
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             DELETE FROM equipment_drop_settings
                             WHERE world_name = ?
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.executeUpdate();

        }

    }

}