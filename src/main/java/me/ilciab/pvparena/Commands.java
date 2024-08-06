package me.ilciab.pvparena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {

    private static Player inviter = null;
    private static Player invited = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Can't execute this message from the console");
            return true;
        }
        Player playerSender = ((Player) sender).getPlayer();
        if (!playerSender.hasPermission("fight.use")) {
            playerSender.sendMessage(ChatColor.RED + "You don't have the permission to execute this command");
            return true;
        }

        if (Fight.isFighting()) {
            playerSender.sendMessage("Another fight is already going on");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Wrong usage of command");
            return true;
        }

        String subcommand = args[0];

        if (subcommand.equals("accept") || subcommand.equals("decline")) {

            if (playerSender != invited) {
                playerSender.sendMessage(ChatColor.RED + "You have no pending invitations");
                return true;
            }

            switch (subcommand) {
                case "accept" -> {
                    inviter.sendMessage(ChatColor.GOLD + invited.getName() + ChatColor.RESET + " accepted the invite");
                    Fight.saveFightersData(inviter, invited);
                    inviter = null;
                    invited = null;
                    Fight.startFight();
                }
                case "decline" -> {
                    inviter.sendMessage(ChatColor.GOLD + invited.getName() + ChatColor.RED + " declined the fight");
                    invited = null;
                }
            }
        } else {
            invited = Bukkit.getPlayer(subcommand);

            if (invited == null) {
                playerSender.sendMessage(ChatColor.RED + "A player with that name does not exist");
                return true;
            }


            inviter = playerSender;
            inviter.sendMessage(ChatColor.GOLD + invited.getName() + ChatColor.RESET + " has been invited, he has " + Main.getFightInviteExpirationTime() + " seconds to accept or decline");
            invited.sendMessage(ChatColor.GOLD + inviter.getName() + ChatColor.RESET + " has challenged you to a fight, do /fight accept or decline");

            new BukkitRunnable() {
                @Override
                public void run() {
                    invited = null;
                    inviter.sendMessage(ChatColor.RED + "Invite time has expired");
                }
            }.runTaskLater(Main.getInstance(), 200L);
        }

        return true;
    }

}
