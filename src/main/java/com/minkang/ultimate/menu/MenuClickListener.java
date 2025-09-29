package com.minkang.ultimate.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickListener implements Listener {
    private final MenuManager menuManager;
    public MenuClickListener(MenuManager menuManager) { this.menuManager = menuManager; }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView() == null || event.getView().getTitle() == null) return;
        String title = event.getView().getTitle();
        // We don't have direct handle to inventory instance, so rely on title match.
        if (!title.equals(MenuManager.color(Main.getInstance().getConfig().getString("menu.title", "&8&lServer Menu")).replace('§','§'))) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        int raw = event.getRawSlot();
        boolean close = menuManager.handleClick(p, raw);
        if (close) p.closeInventory();
    }
}