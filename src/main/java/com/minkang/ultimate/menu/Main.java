package com.minkang.ultimate.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private MenuManager menuManager;
    private ShiftFListener shiftFListener;
    private MenuClickListener menuClickListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig(); // ensure latest
        this.menuManager = new MenuManager(this);
        this.menuManager.loadFromConfig();

        this.shiftFListener = new ShiftFListener(menuManager);
        this.menuClickListener = new MenuClickListener(menuManager);
        Bukkit.getPluginManager().registerEvents(shiftFListener, this);
        Bukkit.getPluginManager().registerEvents(menuClickListener, this);

        getLogger().info(color("&a[MenuPlugin-ShiftF] Enabled. Press &eShift+F &ato open the menu."));
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        getLogger().info(color("&c[MenuPlugin-ShiftF] Disabled."));
    }

    public static Main getInstance() { return instance; }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
