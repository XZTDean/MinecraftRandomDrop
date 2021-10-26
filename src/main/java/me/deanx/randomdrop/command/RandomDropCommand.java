package me.deanx.randomdrop.command;

import me.deanx.randomdrop.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RandomDropCommand implements CommandExecutor {
    private final Plugin plugin;

    public RandomDropCommand(Plugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("RandomDrop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reload();
                return true;
            }
        }
        return false;
    }
}
