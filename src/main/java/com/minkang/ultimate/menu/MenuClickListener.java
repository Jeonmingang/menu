package com.minkang.ultimate.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuClickListener implements Listener {
    private final MenuManager menuManager;
    public MenuClickListener(MenuManager menuManager) { this.menuManager = menuManager; }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getView().getTopInventory();
        if (inv == null) return;
        if (!e.getView().getTitle().equals(menuManager.getTitle())) return;
        e.setCancelled(true);
        if (e.getRawSlot() == e.getSlot() && e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            boolean close = menuManager.handleClick(p, e.getSlot());
            if (close) p.closeInventory();
        }
    }
}
