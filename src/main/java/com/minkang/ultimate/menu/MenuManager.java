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
import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    public enum RunAs { PLAYER, CONSOLE }

    private final Main plugin;
    private Inventory menu;
    private String title;
    private int size;
    private RunAs defaultRunAs = RunAs.PLAYER;

    private final Map<Integer, String> clickCommands = new HashMap<>();
    private final Map<Integer, Boolean> closeOnClick = new HashMap<>();
    private final Map<Integer, RunAs> runAsMap = new HashMap<>();

    public MenuManager(Main plugin) { this.plugin = plugin; }

    public void loadFromConfig() {
        FileConfiguration cfg = plugin.getConfig();
        this.title = color(cfg.getString("menu.title", "&8&lServer Menu"));
        this.size = Math.max(9, cfg.getInt("menu.size", 27));
        this.defaultRunAs = RunAs.valueOf(cfg.getString("menu.default-run-as", "PLAYER").toUpperCase());

        this.menu = Bukkit.createInventory(null, size, color(title));
        clickCommands.clear();
        closeOnClick.clear();
        runAsMap.clear();

        ConfigurationSection itemsSec = cfg.getConfigurationSection("menu.items");
        if (itemsSec != null) {
            for (String key : itemsSec.getKeys(false)) {
                ConfigurationSection is = itemsSec.getConfigurationSection(key);
                if (is == null) continue;
                int slot = is.getInt("slot", -1);
                String matName = is.getString("material", "STONE");
                String name = color(is.getString("name", "&fItem"));
                java.util.List<String> lore = new ArrayList<>();
                for (String l : is.getStringList("lore")) lore.add(color(l));
                String command = is.getString("command", "");
                boolean close = is.getBoolean("close", true);
                String runAsString = is.getString("run-as", null);

                if (slot < 0 || slot >= size) continue;
                Material mat = Material.matchMaterial(matName);
                if (mat == null) mat = Material.STONE;

                ItemStack it = new ItemStack(mat);
                ItemMeta meta = it.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(name);
                    if (!lore.isEmpty()) meta.setLore(lore);
                    it.setItemMeta(meta);
                }
                menu.setItem(slot, it);

                clickCommands.put(slot, command);
                closeOnClick.put(slot, close);
                RunAs ra = defaultRunAs;
                if (runAsString != null) {
                    try { ra = RunAs.valueOf(runAsString.toUpperCase()); } catch (Exception ignored) {}
                }
                runAsMap.put(slot, ra);
            }
        }
    }

    public void open(Player p) { p.openInventory(menu); }

    public boolean handleClick(Player p, int slot) {
        if (slot < 0 || slot >= size) return false;
        if (!clickCommands.containsKey(slot)) return false;

        String cmd = clickCommands.get(slot);
        if (cmd == null || cmd.trim().isEmpty()) return true;

        final String cmdToRun = cmd.replace("{player}", p.getName());

        RunAs ra = runAsMap.getOrDefault(slot, defaultRunAs);
        if (ra == RunAs.CONSOLE) {
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdToRun));
        } else {
            final org.bukkit.entity.Player playerRef = p;
            Bukkit.getScheduler().runTask(plugin, () -> playerRef.performCommand(cmdToRun));
        }

        return closeOnClick.getOrDefault(slot, true);
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}