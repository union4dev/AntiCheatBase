package org.union4dev.anticheat.manager;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import org.union4dev.anticheat.detector.Detector;
import org.union4dev.anticheat.detector.implement.badpackets.BadPacketSlot;
import org.union4dev.anticheat.detector.implement.misc.SetBackConfirm;
import org.union4dev.anticheat.player.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class DetectorManager {

    private final Map<Class<? extends Detector>, Detector> detectorMap = new HashMap<>();

    public DetectorManager(PlayerData player) {
        registerDetector(
                // BadPackets
                new BadPacketSlot(player),

                // Misc
                new SetBackConfirm(player)
        );
    }

    public void registerDetector(Detector... detectors) {
        for (Detector detector : detectors) {
            final Class<? extends Detector> detectorClass = detector.getClass();
            if (!detectorMap.containsKey(detectorClass)) {
                detectorMap.put(detectorClass, detector);
            }
        }
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        detectorMap.values().forEach(detector -> {
            if (detector.isEnabled())
                detector.onPacketReceive(event);
        });
    }

    public void onPacketSend(PacketSendEvent event) {
        detectorMap.values().forEach(detector -> {
            if (detector.isEnabled())
                detector.onPacketSend(event);
        });
    }
}
