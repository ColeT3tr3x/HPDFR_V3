package com.t3tr3x.helix.core;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.t3tr3x.helix.dispatch.CallManager;
import com.t3tr3x.helix.dispatch.Commands;

public class Main extends JavaPlugin {

    private static Main instance;
    private FileControl fc;
    private CallManager cm;


    @Override
    public void onEnable() {
    
        fc = new FileControl(new File(getDataFolder(), "config.yml"));
        cm = new CallManager(this);


        getLogger().info("Starting, HPDFR V: 2.0");
        getLogger().info("[=====================]");
        getLogger().info("[==  LOADED HPDFR   ==]");
        getLogger().info("[= Created by;T3tr3x =]");
        getLogger().info("[=====================]");
        getLogger().warning("DISPATCH: ACTIVATED");
        getLogger().warning("Loading Commands...");
        registerCommands();
        getLogger().info("Loaded Commands!");
    }
    @Override
    public void onDisable() {

        getLogger().info("Ending, HPDFR V: 2.0");
        getLogger().info("[=====================]");
        getLogger().info("[== DISABLED HPDFR ===]");
        getLogger().info("[========= Bye! ========]");
        getLogger().info("[=====================]");
        getLogger().warning("DISPATCH: DISABLED");
        getLogger().warning("Commands UNLOADED...");
        fc.save();

    }
    public CallManager getCallManager()
    {
        return this.cm;
    }

    public static Main getInstance() { return instance; }
    private void registerCommands() {
        this.getCommand("dispatch").setExecutor(new Commands(this));
    }

    public FileControl getConfigfc() {
        return this.fc;
    }

}
