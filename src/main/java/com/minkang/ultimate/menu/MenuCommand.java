package com.minkang.ultimate.menu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuCommand implements CommandExecutor, TabCompleter {
    private final Main plugin;
    private final MenuManager menuManager;

    public MenuCommand(Main plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                menuManager.open((Player) sender);
            } else {
                sender.sendMessage(Main.color("&c플레이어만 사용 가능합니다."));
            }
            return true;
        }

        if ("리로드".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("menu.reload")) {
                sender.sendMessage(Main.color("&c권한이 없습니다. (menu.reload)"));
                return true;
            }
            plugin.reloadConfig();
            menuManager.loadFromConfig();
            sender.sendMessage(Main.color("&a메뉴 설정을 리로드했습니다."));
            return true;
        }

        sender.sendMessage(Main.color("&e사용법: /메뉴 | /메뉴 리로드"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if ("리로드".startsWith(args[0])) list.add("리로드");
            return list;
        }
        return Collections.emptyList();
    }
}
