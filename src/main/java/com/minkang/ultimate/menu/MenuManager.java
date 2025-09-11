package com.minkang.ultimate.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuManager {
    private final Main plugin;
    private Inventory menu;
    private String title;
    private int size;
    // Store actions by slot
    private final java.util.Map<Integer, String> clickCommands = new java.util.HashMap<>();
    private final java.util.Map<Integer, Boolean> closeOnClick = new java.util.HashMap<>();

    public MenuManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadFromConfig() {
        FileConfiguration cfg = plugin.getConfig();
        this.title = color(cfg.getString("menu.title", "&8&lServer Menu"));
        this.size = Math.max(9, cfg.getInt("menu.size", 27));
        // normalize to multiple of 9
        if (size % 9 != 0) {
            size = ((size / 9) + 1) * 9;
        }
        this.menu = Bukkit.createInventory(null, size, title);
        this.clickCommands.clear();
        this.closeOnClick.clear();

        ConfigurationSection items = cfg.getConfigurationSection("menu.items");
        if (items != null) {
            for (String key : items.getKeys(false)) {
                ConfigurationSection it = items.getConfigurationSection(key);
                if (it == null) continue;
                int slot = it.getInt("slot", -1);
                String materialName = it.getString("material", "STONE");
                String name = it.getString("name", "&fItem");
                List<String> lore = it.getStringList("lore");
                String command = it.getString("command", "");
                boolean close = it.getBoolean("close", true);

                if (slot < 0 || slot >= size) continue;
                Material mat = Material.matchMaterial(materialName);
                if (mat == null) mat = Material.STONE;

                ItemStack stack = new ItemStack(mat);
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(color(name));
                    if (lore != null && !lore.isEmpty()) {
                        List<String> colored = new ArrayList<>();
                        for (String line : lore) colored.add(color(line));
                        meta.setLore(colored);
                    }
                    stack.setItemMeta(meta);
                }
                this.menu.setItem(slot, stack);
                this.clickCommands.put(slot, command == null ? "" : command);
                this.closeOnClick.put(slot, close);
            }
        }
    }

    public void open(Player p) {
        p.openInventory(menu);
    }

    public boolean handleClick(Player p, int slot) {
        if (slot < 0 || slot >= size) return false;
        if (!clickCommands.containsKey(slot)) return false;
        String cmd = clickCommands.get(slot);
        boolean close = closeOnClick.getOrDefault(slot, true);
        if (cmd != null && !cmd.trim().isEmpty()) {
            // Run as player
            p.performCommand(cmd.replaceFirst("^/", ""));
        }
        return close;
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getTitle() { return title; }
}
