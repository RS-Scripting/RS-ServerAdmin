package com.rsscripting.serveradmin;

import com.rsscripting.serveradmin.database.DatabaseManager;
import com.rsscripting.serveradmin.database.SettingsDAO;
import com.rsscripting.serveradmin.commands.RSAdminCommand;
import com.rsscripting.serveradmin.keepinventory.KeepInventoryDAO;
import com.rsscripting.serveradmin.listeners.CreeperListener;
import com.rsscripting.serveradmin.security.SecurityManager;
import com.rsscripting.serveradmin.listeners.InventoryListener;
import com.rsscripting.serveradmin.listeners.CreeperEntityDamageListener;
import com.rsscripting.serveradmin.update.GitHubUpdateChecker;

import com.rsscripting.serveradmin.settings.SettingKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.logging.Level;

public class RSServerAdmin extends JavaPlugin {

    private static RSServerAdmin instance;
    private KeepInventoryDAO keepInventoryDAO;
    private DatabaseManager databaseManager;
    private SettingsDAO settingsDAO;
    private SecurityManager securityManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);

        try {

            databaseManager.connect();

            settingsDAO = new SettingsDAO(databaseManager);

            settingsDAO.initializeDefaults();

            keepInventoryDAO = new KeepInventoryDAO(databaseManager);

            securityManager = new SecurityManager(this);

            boolean keepInventoryDefault =
                    settingsDAO.getDefault(
                            SettingKey.KEEP_INVENTORY_NEW_WORLD
                    );

            for (World world : Bukkit.getWorlds()) {

                keepInventoryDAO.ensureWorldExists(
                        world.getName(),
                        keepInventoryDefault
                );

            }

            if (getCommand("rsadmin") != null) {

                getCommand("rsadmin").setExecutor(
                        new RSAdminCommand(this)
                );

            } else {

                getLogger().severe(
                        "Command 'rsadmin' not found in plugin.yml!"
                );

            }

            getServer().getPluginManager().registerEvents(
                    new InventoryListener(),
                    this
            );

            getServer().getPluginManager().registerEvents(
                    new CreeperListener(),
                    this
            );

            getServer().getPluginManager().registerEvents(
                    new CreeperEntityDamageListener(),
                    this
            );


        } catch (Exception ex) {

            getLogger().log(
                    Level.SEVERE,
                    "Failed to initialize database.",
                    ex
            );

            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        new GitHubUpdateChecker(
                this
        ).checkForUpdates();

        getLogger().info("RS-ServerAdmin enabled.");
    }

    @Override
    public void onDisable() {

        if (databaseManager != null) {
            databaseManager.disconnect();
        }

        getLogger().info("RS-ServerAdmin disabled.");
    }

    public KeepInventoryDAO getKeepInventoryDAO() {return keepInventoryDAO;}
    public static RSServerAdmin getInstance() {
        return instance;
    }
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    public SettingsDAO getSettingsDAO() {
        return settingsDAO;
    }
    public SecurityManager getSecurityManager() {
        return securityManager;
    }

}