package com.rsscripting.serveradmin.dao;

import com.rsscripting.serveradmin.database.DatabaseManager;
import com.rsscripting.serveradmin.settings.SettingKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsDAO {

    private final DatabaseManager databaseManager;

    public SettingsDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void initializeDefaults() throws SQLException {

        for (SettingKey key : SettingKey.values()) {

            if (!defaultExists(key)) {

                boolean value = getDefaultValue(key);

                setDefault(key, value);
            }
        }
    }

    private boolean defaultExists(SettingKey key) throws SQLException {

        Connection connection = databaseManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT setting_key FROM defaults WHERE setting_key = ?")) {

            statement.setString(1, key.name());

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean getDefaultValue(SettingKey key) {

        return switch (key) {

            case KEEP_INVENTORY -> true;

            case CREEPER_BLOCK_DAMAGE,
                 CREEPER_ENTITY_DAMAGE,
                 KEEP_INVENTORY_NEW_WORLD -> false;

        };
    }

    public void setDefault(
            SettingKey key,
            boolean value
    ) throws SQLException {

        Connection connection = databaseManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                """
                INSERT OR REPLACE INTO defaults
                (setting_key, value)
                VALUES (?, ?)
                """
        )) {

            statement.setString(1, key.name());
            statement.setInt(2, value ? 1 : 0);

            statement.executeUpdate();
        }

    }

    public boolean getDefault(
            SettingKey key
    ) throws SQLException {

        Connection connection =
                databaseManager.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             """
                             SELECT value
                             FROM defaults
                             WHERE setting_key = ?
                             """
                     )) {

            statement.setString(
                    1,
                    key.name()
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt("value") == 1;

                }

            }

        }

        return false;

    }

    public void toggleDefault(
            SettingKey key
    ) throws SQLException {

        boolean current =
                getDefault(key);

        setDefault(
                key,
                !current
        );

    }

}