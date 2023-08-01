package org.union4dev.anticheat;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiCheat extends JavaPlugin {

    @Override
    public void onLoad() {
        AntiCheatAPI.getInstance().onLoad(this);
    }

    @Override
    public void onEnable() {
        AntiCheatAPI.getInstance().onEnable(this);
    }

    @Override
    public void onDisable() {
        AntiCheatAPI.getInstance().onDisable(this);
    }
}
