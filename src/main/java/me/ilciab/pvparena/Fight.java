package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class Fight implements Listener {



    @Getter
    private static Map<Player, PlayerData> fighters = new HashMap<>();
    @Getter
    private static boolean fighting = false;

    private static PlayerInventory fightInventory;
    public static void saveFightersData(Player p1, Player p2){
        fighters.put(p1, new PlayerData(p1.getInventory().getContents(), p1.getLevel(), p1.getExp()));
        fighters.put(p2, new PlayerData(p2.getInventory().getContents(), p2.getLevel(), p2.getExp()));
    }

    public static void startFight(){
        fighting = true;
        for (Map.Entry<Player, PlayerData> entry : fighters.entrySet()) {
            Player player = entry.getKey();
            player.getInventory().clear();
            player.setLevel(0);
            player.setExp(0);
        }

        //TODO teleport players to the arena
    }

    public void endFight(Player winner, Player loser){
        fighting = false;
        winner.sendMessage("you win");
        loser.sendMessage("you loose");
        for(Map.Entry<Player, PlayerData> entry : fighters.entrySet()){
            Player player = entry.getKey();
            PlayerData playerData = fighters.get(player);
            player.getInventory().setContents(playerData.getContents());
            player.setExp(playerData.getXp());
            player.setLevel(playerData.getLevel());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(fighters.containsKey(event.getEntity().getPlayer()))
            endFight(event.getEntity().getKiller(), event.getEntity());

        fighters.clear();
    }

}
