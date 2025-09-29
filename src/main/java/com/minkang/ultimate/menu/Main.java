package com.minkang.ultimate.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.menuManager = new MenuManager(this);
        this.menuManager.loadFromConfig();

        // Listeners
        Bukkit.getPluginManager().registerEvents(new ShiftFListener(menuManager), this);
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(menuManager), this);
        Bukkit.getPluginManager().registerEvents(new BoundItemListener(this, menuManager), this);

        // Command
        MenuCommand menuCommand = new MenuCommand(this, menuManager);
        getCommand("메뉴").setExecutor(menuCommand);
        getCommand("메뉴").setTabCompleter(menuCommand);

        getLogger().info(color("&a[MenuPlugin-ShiftF] Enabled. Shift+F, /메뉴, /메뉴 아이템"));
    }

    public static Main getInstance() { return instance; }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}