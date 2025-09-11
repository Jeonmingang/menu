package com.minkang.ultimate.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ShiftFListener implements Listener {
    private final MenuManager menuManager;

    public ShiftFListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player p = event.getPlayer();
        // Only when sneaking (Shift held)
        if (p.isSneaking()) {
            // Cancel normal swap and open our menu
            event.setCancelled(true);
            menuManager.open(p);
        }
    }
}
