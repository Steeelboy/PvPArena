package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    /*TODO
        - color messages
        - actually teleport players and do all the fight shit
     */

    @Getter
    private static Plugin instance = null;
    @Getter
    private static int fightInviteExpirationTime;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Fight(), this);
        this.saveDefaultConfig();
        fightInviteExpirationTime = getConfig().getInt("fight-expiration");
        getCommand("fight").setExecutor(new Commands());
    }

}
