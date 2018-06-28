package com.t3tr3x.helix.dispatch;

import static com.t3tr3x.helix.core.ColorUtil.color;

import org.bukkit.Bukkit;
import org.bukkit.event.player.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player;
import com.t3tr3x.helix.dispatch.CallManager.*;

import com.t3tr3x.helix.core.Main;

public class Commands implements CommandExecutor {

	
	private Main main;
	private Commands c;
	
	CallManager cm;
    
    Main units = Main.getInstance();

    public Commands(Main plugin) {
        this.main = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You Can't Dispatch From Console!");
            Player p = (Player) sender;
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(color("Don't know the command? Try /dispatch help"));
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(color("&1&l[======= &4&lHPDFR Dispatch Menu! &1&l=======]"));
            sender.sendMessage(color("&l/dispatch add <Unit> &f-Add a unit!"));
            sender.sendMessage(color("&l/dispatch hire <Player> <Unit> &f-Hire Someone to a unit"));
            sender.sendMessage(color("&l/dispatch call <UnitName> <Message> - Dispatch a unit"));
            sender.sendMessage(color("&1&l[=========== &5&lMade by T3tr3x &1&l=========]"));
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
        	cm.addUnit(args[1]);
        	sender.sendMessage(color("&1&l"+args[1]+" has been created!"));
        	return true;
        }
        if (args[0].equalsIgnoreCase("hire")) {
        	Player target = Bukkit.getPlayer(args[1]); 
        	cm.hire(target, args[2]);
        	sender.sendMessage(color("&1&l"+args[1]+"&f&l has been hired into"+"&1&l"+args[2]));
        } return true;
    }
}