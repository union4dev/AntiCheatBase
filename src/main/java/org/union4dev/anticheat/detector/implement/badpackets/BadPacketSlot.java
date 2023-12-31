package org.union4dev.anticheat.detector.implement.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientHeldItemChange;
import org.union4dev.anticheat.detector.DetectionType;
import org.union4dev.anticheat.detector.Detector;
import org.union4dev.anticheat.player.PlayerData;

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
                flag(3);
            }

            lastSlot = currentSlot;
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
