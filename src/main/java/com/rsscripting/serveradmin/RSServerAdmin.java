package com.rsscripting.serveradmin;

import com.rsscripting.serveradmin.database.DatabaseManager;
import com.rsscripting.serveradmin.database.SettingsDAO;
import com.rsscripting.serveradmin.commands.RSAdminCommand;
import com.rsscripting.serveradmin.keepinventory.KeepInventoryDAO;
import com.rsscripting.serveradmin.listeners.CreeperBlockDamageListener;
import com.rsscripting.serveradmin.security.SecurityManager;
import com.rsscripting.serveradmin.listeners.InventoryListener;
import com.rsscripting.serveradmin.listeners.CreeperEntityDamageListener;
import com.rsscripting.serveradmin.update.GitHubUpdateChecker;
import com.rsscripting.serveradmin.listeners.WorldLoadListener;
import com.rsscripting.serveradmin.creeper.CreeperSettingsDAO;

import com.rsscripting.serveradmin.settings.SettingKey;
import com.rsscripting.serveradmin.worlds.WorldsDAO;
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
    private WorldsDAO worldsDAO;
    private CreeperSettingsDAO creeperSettingsDAO;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);

        try {

            databaseManager.connect();

            settingsDAO = new SettingsDAO(
                    databaseManager
            );

            settingsDAO.initializeDefaults();

            keepInventoryDAO = new KeepInventoryDAO(
                    databaseManager
            );

            worldsDAO = new WorldsDAO(
                    databaseManager
            );

            creeperSettingsDAO =
                    new CreeperSettingsDAO(
                            databaseManager
                    );

            securityManager = new SecurityManager(this);

            boolean keepInventoryDefault =
                    settingsDAO.getDefault(
                            SettingKey.KEEP_INVENTORY_NEW_WORLD
                    );

            for (World world : Bukkit.getWorlds()) {

                worldsDAO.ensureWorldExists(
                        world.getName()
                );

                keepInventoryDAO.ensureWorldExists(
                        world.getName(),
                        keepInventoryDefault
                );

                boolean blockDamageDefault =
                        settingsDAO.getDefault(
                                SettingKey.CREEPER_BLOCK_DAMAGE
                        );

                boolean entityDamageDefault =
                        settingsDAO.getDefault(
                                SettingKey.CREEPER_ENTITY_DAMAGE
                        );

                creeperSettingsDAO.ensureWorldExists(
                        world.getName(),
                        blockDamageDefault,
                        entityDamageDefault
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
                    new CreeperBlockDamageListener(),
                    this
            );

            getServer().getPluginManager().registerEvents(
                    new CreeperEntityDamageListener(),
                    this
            );

            getServer().getPluginManager().registerEvents(
                    new WorldLoadListener(),
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

    public KeepInventoryDAO getKeepInventoryDAO() {
        return keepInventoryDAO;}
    public static RSServerAdmin getInstance() {

        return instance;
    }
    public DatabaseManager getDatabaseManager() {

        return databaseManager;
    }
    public SettingsDAO getSettingsDAO() {

        return settingsDAO;
    }
    public WorldsDAO getWorldsDAO() {
        return worldsDAO;
    }
    public SecurityManager getSecurityManager() {

        return securityManager;
    }
    public CreeperSettingsDAO getCreeperSettingsDAO() {
        return creeperSettingsDAO;
    }

}