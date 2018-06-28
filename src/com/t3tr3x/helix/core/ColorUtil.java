package com.t3tr3x.helix.core;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}