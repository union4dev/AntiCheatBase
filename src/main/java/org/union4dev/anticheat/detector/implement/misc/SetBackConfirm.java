package org.union4dev.anticheat.detector.implement.misc;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPositionAndRotation;
import org.union4dev.anticheat.detector.DetectionType;
import org.union4dev.anticheat.detector.Detector;
import org.union4dev.anticheat.player.PlayerData;
import org.union4dev.anticheat.util.dataset.PlayerLocation;

public class SetBackConfirm extends Detector {

    public SetBackConfirm(PlayerData player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION) {
            if (shouldCancel()) event.setCancelled(true);
        } else if (event.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION) {
            if (shouldCancel()) event.setCancelled(true);
        } else if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            final WrapperPlayClientPlayerPositionAndRotation packet = new WrapperPlayClientPlayerPositionAndRotation(event);
            if (getPlayer().getTeleportManager().isTeleporting()) {
                if (isClientReply(packet)) {
                    getPlayer().getTeleportManager().setTeleporting(false);
                } else event.setCancelled(true);
            }
        }
    }

    private boolean isClientReply(WrapperPlayClientPlayerPositionAndRotation packet) {
        final PlayerLocation lastTeleport = getPlayer().getTeleportManager().getLastTeleport();
        final Location location = packet.getLocation();
        return location.getX() == lastTeleport.getX() && location.getY() == lastTeleport.getY() && location.getZ() == lastTeleport.getZ() && packet.getYaw() == lastTeleport.getYaw() && packet.getPitch() == lastTeleport.getPitch();
    }

    private boolean shouldCancel() {
        return getPlayer().getTeleportManager().isTeleporting();
    }

    @Override
    public String getName() {
        return "SetBackConfirm";
    }

    @Override
    public String getDetail() {
        return "No flag";
    }

    @Override
    public DetectionType getType() {
        return DetectionType.MISC;
    }
}
