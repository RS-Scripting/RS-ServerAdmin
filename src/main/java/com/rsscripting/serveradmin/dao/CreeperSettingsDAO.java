package com.rsscripting.serveradmin.dao;

import com.rsscripting.serveradmin.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreeperSettingsDAO {

    private final DatabaseManager databaseManager;

    public CreeperSettingsDAO(
            DatabaseManager databaseManager
    ) {

        this.databaseManager =
                databaseManager;

    }

    public void ensureWorldExists(
            String worldName,
            boolean blockDamage,
            boolean entityDamage
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             INSERT OR IGNORE INTO
                             creeper_settings
                             (
                                 world_name,
                                 block_damage,
                                 entity_damage
                             )
                             VALUES (?, ?, ?)
                             """
                     )) {

            statement.setString(
                    1,
                    worldName
            );

            statement.setInt(
                    2,
                    blockDamage ? 1 : 0
            );

            statement.setInt(
                    3,
                    entityDamage ? 1 : 0
            );

            statement.executeUpdate();

        }

    }

    public boolean getBlockDamage(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT block_damage
                             FROM creeper_settings
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
                            "block_damage"
                    ) == 1;

                }

            }

        }

        return false;

    }

    public void setBlockDamage(
            String worldName,
            boolean value
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             UPDATE creeper_settings
                             SET block_damage = ?
                             WHERE world_name = ?
                             """
                     )) {

            statement.setInt(
                    1,
                    value ? 1 : 0
            );

            statement.setString(
                    2,
                    worldName
            );

            statement.executeUpdate();

        }

    }

    public boolean getEntityDamage(
            String worldName
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT entity_damage
                             FROM creeper_settings
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
                            "entity_damage"
                    ) == 1;

                }

            }

        }

        return false;

    }

    public void setEntityDamage(
            String worldName,
            boolean value
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             UPDATE creeper_settings
                             SET entity_damage = ?
                             WHERE world_name = ?
                             """
                     )) {

            statement.setInt(
                    1,
                    value ? 1 : 0
            );

            statement.setString(
                    2,
                    worldName
            );

            statement.executeUpdate();

        }

    }

}