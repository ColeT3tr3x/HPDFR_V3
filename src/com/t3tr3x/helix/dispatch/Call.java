package com.t3tr3x.helix.dispatch;

import static com.t3tr3x.helix.core.ColorUtil.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

public class Call {

    private CallManager cm;
    private Player target;
    private List<Player> unitPlayers;
    private String unitType;
    private String callMessage;
    private boolean finished = true;

    public Call(CallManager cm, Player target, String callMessage, String unitType, int unitsNeeded)
    {
        this.cm = cm;
        this.target = target;
        this.callMessage = callMessage;
        this.unitType = unitType;
        this.unitPlayers = new ArrayList<>();
        List<Player> playersInUnit = cm.getAvailableUnits(unitType);
        for(int i = 0; i < unitsNeeded; i++)
        {
            unitPlayers.add(playersInUnit.get(new Random().nextInt(playersInUnit.size())));
            playersInUnit.remove(unitPlayers.get(i));
        }
        unitPlayers.forEach(player -> player.sendMessage(color(("&4&lDispatch:" + "&b " + target.getName() + " &6Needs Help! &o| &6" + callMessage))));
        unitPlayers.forEach(player -> player.sendMessage(color("&b" + target.getName() + "'s location: &6" + target.getWorld().getName() + ": " + target.getLocation().getBlockX() + "," + target.getLocation().getBlockY() + "," + target.getLocation().getBlockZ())));
        target.sendMessage(color("&cDispatch has sent " + unitsNeeded + " " + unitType + " units to your location. They will be there shortly."));
        finished = false;
    }

    public void finish()
    {
        this.finished = true;
    }

    public boolean isFinished()
    {
        return this.finished;
    }

    public boolean containsPlayer(Player player)
    {
        if(unitPlayers.isEmpty()) return false;
        if(!unitPlayers.contains(player)) return false;
        return true;
    }

    public void removePlayer(Player player)
    {
        if(containsPlayer(player))
            unitPlayers.remove(player);
    }

    public String getUnitType()
    {
        return this.unitType;
    }

    public String getMessage()
    {
        return this.callMessage;
    }

    public Player getTargetPlayer()
    {
        return this.target;
    }
}
