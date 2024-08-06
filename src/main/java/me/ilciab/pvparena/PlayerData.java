package me.ilciab.pvparena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
public class PlayerData {

    private final ItemStack[] contents;
    private final int level;
    private final float xp;

    public PlayerData(ItemStack[] contents, int level, float xp) {
        this.contents = contents;
        this.level = level;
        this.xp = xp;
    }
}
