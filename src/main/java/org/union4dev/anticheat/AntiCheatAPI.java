package org.union4dev.anticheat;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.union4dev.anticheat.listener.ConnectionListener;
import org.union4dev.anticheat.listener.PacketListener;
import org.union4dev.anticheat.manager.ConfigManager;
import org.union4dev.anticheat.player.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class AntiCheatAPI {

    private static final AntiCheatAPI INSTANCE = new AntiCheatAPI();

    private final Map<User, PlayerData> playerDataMap = new HashMap<>();
    private JavaPlugin plugin;

    private ConfigManager configManager;

    public void onLoad(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigManager();
        playerDataMap.clear();
        packetEventsOnLoad();
    }

    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;
        packetEventsOnEnable();
    }

    public void onDisable(JavaPlugin plugin) {
        this.plugin = plugin;
        packetEventsOnDisable();
    }

    private void packetEventsOnLoad() {
        plugin.getLogger().info("EventPackets loading...");
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().getSettings().bStats(true).checkForUpdates(false).debug(false);
        PacketEvents.getAPI().load();
    }

    private void packetEventsOnEnable() {
        plugin.getLogger().info("EventPackets initializing...");
        PacketEvents.getAPI().getEventManager().registerListener(new ConnectionListener());
        PacketEvents.getAPI().getEventManager().registerListener(new PacketListener());
        PacketEvents.getAPI().init();
    }

    private void packetEventsOnDisable() {
        plugin.getLogger().info("EventPackets terminating...");
        PacketEvents.getAPI().terminate();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Map<User, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    public static AntiCheatAPI getInstance() {
        return INSTANCE;
    }
}
