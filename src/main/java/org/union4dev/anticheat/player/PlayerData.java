package org.union4dev.anticheat.player;

import com.github.retrooper.packetevents.protocol.player.User;
import org.union4dev.anticheat.manager.DetectorManager;
import org.union4dev.anticheat.manager.TeleportManager;

import java.util.UUID;

public class PlayerData {
    private final User user;
    private final UUID uniqueId;
    private final DetectorManager detectorManager;
    private final TeleportManager teleportManager;

    public PlayerData(User user) {
        this.user = user;
        this.uniqueId = user.getUUID();
        this.detectorManager = new DetectorManager(this);
        this.teleportManager = new TeleportManager(this);
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public DetectorManager getDetectorManager() {
        return detectorManager;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public User getUser() {
        return user;
    }
}
