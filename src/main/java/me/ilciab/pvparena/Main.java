package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    /*TODO
        - color messages
        - add a config file
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

    public static Player getPlayerByName(String name){
        return Bukkit.getPlayer(name);
    }



}
