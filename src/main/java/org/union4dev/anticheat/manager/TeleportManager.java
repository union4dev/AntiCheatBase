package org.union4dev.anticheat.manager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.union4dev.anticheat.player.PlayerData;
import org.union4dev.anticheat.util.dataset.PlayerLocation;
import org.union4dev.anticheat.util.player.PlayerUtil;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager {
    private final Map<Integer, PlayerLocation> teleportMap = new HashMap<>();
    private final PlayerData player;

    private PlayerLocation lastTeleport;
    private long lastTeleportTime;
    private boolean teleporting;

    public TeleportManager(PlayerData player) {
        this.player = player;
    }

    public void teleport(int backtrack) {
        if (teleporting) return;
        PlayerLocation location = teleportMap.getOrDefault(teleportMap.size() - backtrack - 1, null);
        if (location == null) return;
        final Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
        if (bukkitPlayer == null) return;
        if (lastTeleport != null && lastTeleport.getDistance(location) < 5 && System.currentTimeMillis() - lastTeleportTime < 1500 && !PlayerUtil.isAboveVoid(bukkitPlayer, location.getX(), location.getY(), location.getZ())) location = lastTeleport;

        final Location tpLocation = new Location(bukkitPlayer.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        final Chunk chunkAt = bukkitPlayer.getWorld().getChunkAt(tpLocation);
        if (!bukkitPlayer.getWorld().isChunkLoaded(chunkAt))
            bukkitPlayer.getWorld().loadChunk(chunkAt);

        bukkitPlayer.teleport(tpLocation);
        lastTeleport = location;
        lastTeleportTime = System.currentTimeMillis();
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

    public void putTeleport(int tick, PlayerLocation location) {
        teleportMap.put(tick, location);
    }

    public Map<Integer, PlayerLocation> getTeleportMap() {
        return teleportMap;
    }
}
