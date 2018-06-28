package com.t3tr3x.helix.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.t3tr3x.helix.core.Main;

import java.io.File;
import java.io.IOException;

public class FileControl {

    private File file;
    private FileConfiguration config;

    public FileControl()
    {
        FileControl.checkBaseFiles();
    }

    public FileControl(File f)
    {
        FileControl.checkBaseFiles();
        this.file = f;
        if(!f.exists())
        {
            try{
                f.createNewFile();
            }
            catch(Exception e){}
        }
        this.config = YamlConfiguration.loadConfiguration(f);
    }

    public FileControl setConfig(File file)
    {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        return this;
    }

    public FileControl setFile(File file)
    {
        this.file = file;
        return this;
    }

    public File getFile()
    {
        return this.file;
    }

    public void save()
    {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(FileConfiguration config)
    {
        try {
            config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(File file)
    {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        return this.config;
    }

    public FileConfiguration getConfig()
    {
        if (this.config == null)
            this.config = YamlConfiguration.loadConfiguration(file);
        return this.config;
    }

    public static void checkBaseFiles()
    {
        Main main = Main.getPlugin(Main.class);
        if(!main.getDataFolder().exists())
            main.getDataFolder().mkdirs();
        File file = new File(main.getDataFolder(), "units.yml");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            }
            catch(Exception e) {}
        }
        file = new File(main.getDataFolder(), "config.yml");
        if(!file.exists())
        {
            try {
                file.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.save(file);
            }
            catch(Exception e) {}
        }
    }


}