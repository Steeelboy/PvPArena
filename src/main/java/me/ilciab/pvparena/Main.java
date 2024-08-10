package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    /*TODO
        - color messages
        - actually teleport players and do all the fight shit
     */

    @Getter
    private static Plugin instance = null;
    @Getter
    private static int fightInviteExpirationTime;
    @Getter
    private static Location player1Location;
    @Getter
    private static Location player2Location;
    @Getter
    private static final ItemStack[] fightContent = new ItemStack[36];

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Fight(), this);
        this.saveDefaultConfig();
        fightInviteExpirationTime = getConfig().getInt("fight-expiration");
        player1Location = stringToLocation(getConfig().getString("player1-arena-location"));
        player2Location = stringToLocation(getConfig().getString("player2-arena-location"));
        if (player1Location == null || player2Location == null) {
            getInstance().getLogger().severe("One or more locations are not configured correctly");
        }
        getCommand("fight").setExecutor(new Commands());
    }

    public static Location stringToLocation(String string) {

        double x;
        double y;
        double z;
        float yaw;
        float pitch;

        String[] coordinates = string.split(" ");
        x = Double.parseDouble(coordinates[0]);
        y = Double.parseDouble(coordinates[1]);
        z = Double.parseDouble(coordinates[2]);
        yaw = Float.parseFloat(coordinates[3]);
        pitch = Float.parseFloat(coordinates[4]);

        return new Location(Bukkit.getWorld("nikoSMP_S1"), x, y, z, yaw, pitch);
    }


}
