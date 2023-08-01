package org.union4dev.anticheat.detector.implement.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientHeldItemChange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.union4dev.anticheat.detector.DetectionType;
import org.union4dev.anticheat.detector.Detector;
import org.union4dev.anticheat.player.PlayerData;
import org.union4dev.anticheat.util.dataset.PlayerLocation;
import org.union4dev.anticheat.util.dataset.TeleportType;

public class BadPacketSlot extends Detector {

    private int lastSlot = -1;
    private int currentSlot = -1;

    public BadPacketSlot(PlayerData player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.HELD_ITEM_CHANGE) {
            final WrapperPlayClientHeldItemChange packet = new WrapperPlayClientHeldItemChange(event);

            currentSlot = packet.getSlot();
            if (currentSlot == lastSlot) {
                flag();
            }

            lastSlot = currentSlot;
            final Player player = Bukkit.getPlayer(getPlayer().getUniqueId());
            if (player == null) return;
            final Location location = player.getLocation();
            getPlayer().getTeleportManager().putTeleport(TeleportType.SLOT, new PlayerLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()));
        }
    }

    @Override
    public String getName() {
        return "BadPacketSlot";
    }

    @Override
    public String getDetail() {
        return "lastSlot=" + lastSlot + ", currentSlot=" + currentSlot;
    }

    @Override
    public DetectionType getType() {
        return DetectionType.BAD_PACKETS;
    }
}
