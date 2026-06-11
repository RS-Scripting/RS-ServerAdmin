package com.rsscripting.serveradmin.security;

import com.rsscripting.serveradmin.RSServerAdmin;

import java.util.List;
import java.util.UUID;

public class SecurityManager {

    private final RSServerAdmin plugin;

    public SecurityManager(RSServerAdmin plugin) {
        this.plugin = plugin;
    }

    public UserRole getRole(UUID uuid) {

        String uuidString = uuid.toString();

        List<String> owners =
                plugin.getConfig().getStringList("security.owners");

        if (owners.contains(uuidString)) {
            return UserRole.OWNER;
        }

        List<String> admins =
                plugin.getConfig().getStringList("security.admins");

        if (admins.contains(uuidString)) {
            return UserRole.ADMIN;
        }

        return UserRole.NONE;
    }

    public boolean canAccess(UUID uuid) {
        return getRole(uuid) != UserRole.NONE;
    }

    public boolean isOwner(UUID uuid) {
        return getRole(uuid) == UserRole.OWNER;
    }

    public boolean isAdmin(UUID uuid) {
        UserRole role = getRole(uuid);

        return role == UserRole.ADMIN
                || role == UserRole.OWNER;
    }
}