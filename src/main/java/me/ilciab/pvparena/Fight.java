package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fight implements Listener {

    @Getter
    private static Map<Player, PlayerData> fighters = new HashMap<>();
    @Getter
    private static boolean fighting = false;

    public static void saveFightersData(Player p1, Player p2) {
        fighters.put(p1, new PlayerData(p1.getInventory().getContents(), p1.getLevel(), p1.getExp(),
                p1.getLocation(), p1.getHealth(), p1.getSaturation(), p1.getFoodLevel(), p1.getGameMode()));
        fighters.put(p2, new PlayerData(p2.getInventory().getContents(), p2.getLevel(), p2.getExp(),
                p2.getLocation(), p2.getHealth(), p2.getSaturation(), p2.getFoodLevel(), p2.getGameMode()));
    }

    public static void startFight() {
        fighting = true;
        int i = 0;
        for (Map.Entry<Player, PlayerData> entry : fighters.entrySet()) {
            Player player = entry.getKey();
            player.getInventory().clear();
            player.setLevel(0);
            player.setExp(0);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.teleport(i == 0 ? Main.getPlayer1Location() : Main.getPlayer2Location());

            PlayerInventory playerInventory = player.getInventory();

            playerInventory.setHelmet(new ItemStack(Material.IRON_HELMET));
            playerInventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            playerInventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            playerInventory.setBoots(new ItemStack(Material.IRON_BOOTS));
            playerInventory.setItem(0, new ItemStack(Material.IRON_SWORD));
            playerInventory.setItem(1, new ItemStack(Material.IRON_AXE));
            playerInventory.setItemInOffHand(new ItemStack(Material.SHIELD));

            i++;
        }
    }

    public void endFight(Player winner, Player loser) {
        fighting = false;
        winner.sendMessage("you win");
        loser.sendMessage("you loose");
        for (Map.Entry<Player, PlayerData> entry : fighters.entrySet()) {
            Player player = entry.getKey();
            PlayerData playerData = fighters.get(player);
            player.getInventory().setContents(playerData.getContents());
            player.setExp(playerData.getXp());
            player.setLevel(playerData.getLevel());
            player.teleport(playerData.getLocation());
            player.setHealth(playerData.getHealth());
            player.setSaturation(playerData.getSaturation());
            player.setFoodLevel(playerData.getFoodLevel());
            player.setGameMode(playerData.getGameMode());
        }
        fighters.clear();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        if (!(event.getDamager() instanceof Player))
            return;
        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (!fighters.containsKey(damaged))
            return;
        if ((damaged.getHealth() - event.getDamage()) <= 0) {
            //Killed
            event.setCancelled(true);
            Location bedSpawnLocation = damaged.getRespawnLocation();
            damaged.teleport(Objects.requireNonNullElseGet(bedSpawnLocation, () -> damaged.getWorld().getSpawnLocation()));
            endFight(damager, damaged);
        }
    }
}