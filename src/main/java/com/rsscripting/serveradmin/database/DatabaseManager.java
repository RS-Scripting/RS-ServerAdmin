package com.rsscripting.serveradmin.database;

import com.rsscripting.serveradmin.RSServerAdmin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final RSServerAdmin plugin;
    private Connection connection;

    public DatabaseManager(RSServerAdmin plugin) {
        this.plugin = plugin;
    }

    public void connect() throws SQLException {

        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists()) {

            if (!dataFolder.mkdirs()) {

                throw new SQLException(
                        "Failed to create plugin data folder."
                );

            }

        }

        File databaseFile = new File(
                dataFolder,
                plugin.getConfig().getString(
                        "database.file",
                        "serveradmin.db"
                )
        );

        connection = DriverManager.getConnection(
                "jdbc:sqlite:" + databaseFile.getAbsolutePath()
        );

        createTables();
    }

    private void createTables() throws SQLException {

        try (Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS settings (
                        world_name TEXT NOT NULL,
                        setting_key TEXT NOT NULL,
                        value INTEGER NOT NULL,
                        PRIMARY KEY(world_name, setting_key)
                    )
                    """);

            statement.execute("""
            CREATE TABLE IF NOT EXISTS worlds (
            world_name TEXT PRIMARY KEY
            )
            """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS defaults (
                        setting_key TEXT PRIMARY KEY,
                        value INTEGER NOT NULL
                    )
                    """);

            statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS keep_inventory
                    (
                        world_name TEXT PRIMARY KEY,
                        enabled INTEGER NOT NULL
                    )
                    """
            );

            statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS creeper_settings (
                        world_name TEXT PRIMARY KEY,
                        block_damage INTEGER NOT NULL,
                        entity_damage INTEGER NOT NULL
                    )
                    """
            );

            statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS equipment_drop_settings (
                        world_name TEXT PRIMARY KEY,
                        helmet INTEGER NOT NULL,
                        chestplate INTEGER NOT NULL,
                        leggings INTEGER NOT NULL,
                        boots INTEGER NOT NULL,
                        main_hand INTEGER NOT NULL,
                        off_hand INTEGER NOT NULL
                    )
                    """
            );

        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {

        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }
}