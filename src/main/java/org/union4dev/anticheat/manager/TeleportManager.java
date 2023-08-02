package org.union4dev.anticheat.manager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.union4dev.anticheat.player.PlayerData;
import org.union4dev.anticheat.util.dataset.PlayerLocation;
import org.union4dev.anticheat.util.dataset.TeleportType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeleportManager {
    private final Map<TeleportType, PlayerLocation> teleportMap = new HashMap<>();
    private final PlayerData player;

    private PlayerLocation lastTeleport;
    private boolean teleporting;

    public TeleportManager(PlayerData player) {
        this.player = player;
    }

    public void teleport(TeleportType type) {
        if (teleporting) return;
        final PlayerLocation location = teleportMap.getOrDefault(type, null);
        if (location == null) return;

        final List<TeleportType> teleportTypes = new ArrayList<>(teleportMap.keySet());
        for (TeleportType teleportType : teleportTypes) {
            teleportMap.put(teleportType, location);
        }

        final Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
        if (bukkitPlayer == null) return;
        final Location tpLocation = new Location(bukkitPlayer.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        final Chunk chunkAt = bukkitPlayer.getWorld().getChunkAt(tpLocation);
        if (!bukkitPlayer.getWorld().isChunkLoaded(chunkAt))
            bukkitPlayer.getWorld().loadChunk(chunkAt);

        bukkitPlayer.teleport(tpLocation);
        lastTeleport = location;
        teleporting = true;
    }

    public boolean isTeleporting() {
        return teleporting;
    }

    public void setTeleporting(boolean teleporting) {
        this.teleporting = teleporting;
    }

    public PlayerLocation getLastTeleport() {
        return lastTeleport;
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void putTeleport(TeleportType type, PlayerLocation location) {
        teleportMap.put(type, location);
    }

    public Map<TeleportType, PlayerLocation> getTeleportMap() {
        return teleportMap;
    }
}
