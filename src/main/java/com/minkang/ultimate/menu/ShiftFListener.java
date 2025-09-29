package com.minkang.ultimate.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ShiftFListener implements Listener {
    private final MenuManager menuManager;
    public ShiftFListener(MenuManager menuManager) { this.menuManager = menuManager; }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player p = event.getPlayer();
        if (p.isSneaking()) {
            event.setCancelled(true);
            menuManager.open(p);
        }
    }
}