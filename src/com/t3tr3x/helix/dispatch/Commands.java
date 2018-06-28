package com.t3tr3x.helix.dispatch;

import static com.t3tr3x.helix.core.ColorUtil.color;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
	private CallManager cm;
    
    public Commands(Main plugin) {
        this.main = plugin;
        cm = this.main.getcm();
        
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You Can't Dispatch From Console!");
            
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
        	sender.sendMessage(color("&5&l"+args[1]+" &f&lhas been created!"));
        	return true;
        }
        if (args[0].equalsIgnoreCase("hire")) {
        	Player target = Bukkit.getPlayer(args[1]); 
        	cm.hire(target, args[2]);
        sender.sendMessage(color("&5&l"+args[1]+"&f&l has been hired into"+"&5&l"+args[2]));
        return true;
        }
        if (args[0].equalsIgnoreCase("fire")) {
        	Player target = Bukkit.getPlayer(args[1]);
        			cm.fire(target);
        }
        if (args.length < 5) {
        	return true; 
        }
        if (args[0].equalsIgnoreCase("call")) {
        	if (!Bukkit.getPlayer(args[1]).isOnline()) { return true; }
        		if (!cm.isUnitType(args[2])) {
        			sender.sendMessage("It passed 0"); //Stops here
        		return true;
        	} if (!StringUtils.isNumeric(args[3])) {
        		sender.sendMessage("It passed 1");
        		return true; }
        		String msg = Arrays.asList(args).stream().skip(4).collect(Collectors.joining(" "));
        		cm.startCall((Player) sender, Bukkit.getPlayer(args[1]), msg, args[2], Integer.parseInt(args[3]));
        		sender.sendMessage(color("&6You have dispatched &c"+args[3]+" "+args[2]+"&6's with the message: &c"+msg));
        		return true;
        
        	
        	}
    return true;
   }
}