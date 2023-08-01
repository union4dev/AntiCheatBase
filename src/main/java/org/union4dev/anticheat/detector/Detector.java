package org.union4dev.anticheat.detector;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.union4dev.anticheat.AntiCheatAPI;
import org.union4dev.anticheat.player.PlayerData;
import org.union4dev.anticheat.util.dataset.TeleportType;

public abstract class Detector {

    private final PlayerData player;

    private boolean enabled;
    private int vl;
    private boolean setbackEnabled;
    private int setbackVl;

    public Detector(PlayerData player) {
        this.player = player;
        this.enabled = AntiCheatAPI.getInstance().getConfigManager().getConfig().getBooleanElse(String.format("detections.%s.%s.enabled", getType().display(), getName()), false);
        this.setbackEnabled = AntiCheatAPI.getInstance().getConfigManager().getConfig().getBooleanElse(String.format("detections.%s.%s.setback.enabled", getType().display(), getName()), false);
        this.setbackVl = AntiCheatAPI.getInstance().getConfigManager().getConfig().getIntElse(String.format("detections.%s.%s.setback.vl", getType().display(), getName()), 30);
    }

    public void flag() {
        vl++;
        printFlag();
        setBackProcess();
    }

    private void setBackProcess() {
        if (setbackEnabled && vl > setbackVl) {
            player.getTeleportManager().teleport(TeleportType.SLOT);
        }
    }

    private void printFlag() {
        if (AntiCheatAPI.getInstance().getConfigManager().getConfig().getBooleanElse("flag-logger.enabled", false)) {
            String stringElse = AntiCheatAPI.getInstance().getConfigManager().getConfig().getStringElse("flag-logger.log-style", "");
            stringElse = stringElse.replace("%player%", player.getUser().getName());
            stringElse = stringElse.replace("%detector%", getName());
            stringElse = stringElse.replace("%vl%", String.valueOf(vl));
            stringElse = stringElse.replace("%detail%", getDetail());
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', stringElse));
        }
    }

    public PlayerData getPlayer() {
        return player;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getVl() {
        return vl;
    }

    public int getSetbackVl() {
        return setbackVl;
    }

    public void setSetbackVl(int setbackVl) {
        this.setbackVl = setbackVl;
    }

    public boolean isSetbackEnabled() {
        return setbackEnabled;
    }

    public void setSetbackEnabled(boolean setbackEnabled) {
        this.setbackEnabled = setbackEnabled;
    }

    public void onPacketReceive(PacketReceiveEvent event) {
    }

    public void onPacketSend(PacketSendEvent event) {
    }

    public abstract String getName();
    public abstract String getDetail();
    public abstract DetectionType getType();
}
