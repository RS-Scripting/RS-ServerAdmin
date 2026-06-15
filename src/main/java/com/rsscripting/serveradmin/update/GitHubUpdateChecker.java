package com.rsscripting.serveradmin.update;

import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;



public class GitHubUpdateChecker {

    private final JavaPlugin plugin;
    private final String pomUrl;
    private volatile boolean updateAvailable = false;
    private volatile String latestVersion = null;

    private static final String POM_URL =
            "https://raw.githubusercontent.com/RS-Scripting/RS-ServerAdmin/main/pom.xml";

    public GitHubUpdateChecker(
            JavaPlugin plugin
    ) {

        this.plugin = plugin;
        this.pomUrl = POM_URL;

    }

    public void checkForUpdates() {

        plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(
                        plugin,
                        () -> {

                            try {

                                String currentVersion =
                                        plugin.getDescription()
                                                .getVersion();

                                String latestVersion =
                                        getLatestVersion();

                                if (latestVersion == null) {

                                    plugin.getLogger().warning(
                                            "Unable to determine latest version."
                                    );

                                    return;

                                }

                                if (currentVersion.equals(
                                        latestVersion
                                )) {

                                    updateAvailable = false;

                                    plugin.getLogger().info(
                                            "RS-ServerAdmin is up to date."
                                    );

                                } else {

                                    updateAvailable = true;

                                    this.latestVersion =
                                            latestVersion;

                                    plugin.getLogger().warning(
                                            "Update available! Current: "
                                                    + currentVersion
                                                    + " Latest: "
                                                    + latestVersion
                                    );

                                }

                            } catch (Exception ex) {

                                plugin.getLogger().warning(
                                        "Update check failed."

                                );

                            }

                        }
                );

    }

    private String getLatestVersion()
            throws Exception {

        URL url =
                new URL(
                        POM_URL
                );

        var document =
                DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(
                                url.openStream()
                        );

        var versions =
                document.getElementsByTagName(
                        "version"
                );

        if (versions.getLength() == 0) {

            return null;

        }

        return versions
                .item(0)
                .getTextContent()
                .trim();

    }

    public boolean isUpdateAvailable() {

        return updateAvailable;

    }

    public String getLatestVersionFound() {

        return latestVersion;

    }

}