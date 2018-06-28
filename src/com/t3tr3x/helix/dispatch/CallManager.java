package com.t3tr3x.helix.dispatch;

import static com.t3tr3x.helix.core.ColorUtil.color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.t3tr3x.helix.core.FileControl;
import com.t3tr3x.helix.core.Main;

public class CallManager{
	
	private static CallManager instance;

	private Main main;
	private Commands coms;
	private List<Call> calls;
	private FileControl unitStorage;

	public CallManager(Main main)
	{
		this.main = main;
		this.unitStorage = new FileControl(new File(main.getDataFolder(), "units.yml"));
	}
	public void startCall(Player dispatcher, Player target, String msg, String unitType, int unitsNeeded)
	{
		List<Player> available = getAvailableUnits(unitType);
		if(available.isEmpty())
		{
			dispatcher.sendMessage(color("&cAll available "  + unitType + " units are currently busy."));
			return;
		}
		if(available.size() < unitsNeeded)
		{
			dispatcher.sendMessage(color("&cThere are not enough available " + unitType + " units for that call. " + unitType + " units available: " + available.size()));
			return;
		}
		calls.add(new Call(this, target, msg, unitType, unitsNeeded));
	}
	@SuppressWarnings("deprecation") //UNIT STORAGE
	public List<Player> getAvailableUnits(String unitType)
	{
		List<Player> available = new ArrayList<>();
		if(!unitStorage.getConfig().contains(unitType))
			return available;
		for(String names : unitStorage.getConfig().getStringList(unitType))
		{
			if(Bukkit.getOfflinePlayer(names).isOnline())
				available.add((Player) Bukkit.getOfflinePlayer(names));
		}
		return available;
	}
	public String getUnitType(Player player)
	{
	if(unitStorage.getConfig().getKeys(false).size() == 0) return null;
	for(String unit : unitStorage.getConfig().getKeys(false))
	{
	if(unitStorage.getConfig().getStringList(unit).isEmpty()) continue;
	if(unitStorage.getConfig().getStringList(unit).contains(player.getName())) return unit;
	}
	return null;
	} 
	public void addUnit(String unitType)
	{
	if(!unitStorage.getConfig().contains(unitType))
	{
	unitStorage.getConfig().set(unitType, new ArrayList<String>());
	unitStorage.save();
	}
	}
	public void hire(Player player, String unitType)
	{
	if(!unitStorage.getConfig().contains(unitType)) return;
	if(getUnitType(player) != null) return;
	List<String> unitPlayers = unitStorage.getConfig().getStringList(unitType);
	unitPlayers.add(player.getName());
	unitStorage.getConfig().set(unitType, unitPlayers);
	unitStorage.save();
	}
	public void fire(Player player)
	{
	if(getUnitType(player) == null) return;
	String unitType = getUnitType(player);
	List<String> unitPlayers = unitStorage.getConfig().getStringList(unitType);
	unitPlayers.remove(player.getName());
	unitStorage.getConfig().set(unitType, unitPlayers);
	unitStorage.save();
	}
	public boolean isOnCall(Player player)
	{
		if(calls.isEmpty()) return false;
		for(Call call : calls)
		{
			if(call.containsPlayer(player)) return true;
		}
		return false;
	}
	public static CallManager getInstance() { return instance; }
}
