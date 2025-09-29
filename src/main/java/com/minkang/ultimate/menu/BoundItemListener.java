package com.minkang.ultimate.menu;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BoundItemListener implements Listener {
    private final MenuManager menuManager;
    private final NamespacedKey key;

    public BoundItemListener(Main plugin, MenuManager menuManager) {
        this.menuManager = menuManager;
        this.key = new NamespacedKey(plugin, "menu_item");
    }

    public static void markItemAsMenuOpener(Main plugin, ItemStack item) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        NamespacedKey k = new NamespacedKey(plugin, "menu_item");
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(k, PersistentDataType.BYTE, (byte)1);
        item.setItemMeta(meta);
    }

    public static boolean isMenuOpener(Main plugin, ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        NamespacedKey k = new NamespacedKey(plugin, "menu_item");
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Byte b = pdc.get(k, PersistentDataType.BYTE);
        return b != null && b == (byte)1;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action a = event.getAction();
        if (a != Action.RIGHT_CLICK_AIR && a != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (!isMenuOpener(Main.getInstance(), item)) return;

        Player p = event.getPlayer();
        event.setCancelled(true); // prevent normal use
        menuManager.open(p);
    }
}