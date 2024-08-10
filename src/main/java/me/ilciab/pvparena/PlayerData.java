package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Getter
public class PlayerData {

    private final ItemStack[] contents;
    private final int level;
    private final float xp;
    private final Location location;
    private final double health;
    private final float saturation;
    private final int foodLevel;
    private final GameMode gameMode;

    public PlayerData(ItemStack[] contents, int level, float xp, Location location, double health, float saturation, int foodLevel, GameMode gameMode) {
        this.contents = contents;
        this.level = level;
        this.xp = xp;
        this.location = location;
        this.health = health;
        this.saturation = saturation;
        this.foodLevel = foodLevel;
        this.gameMode = gameMode;
    }
}
