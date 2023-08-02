package org.union4dev.anticheat.util.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static boolean isAboveVoid(Player player, double x, double y, double z) {
        if (y < 0) return true;
        for (int i = (int) y; i > 0; --i) {
            if (!(player.getWorld().getBlockAt((int) x, i, (int) z).getType() == Material.AIR)) {
                return false;
            }
        }
        return true;
    }
}
