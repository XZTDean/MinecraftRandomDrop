package me.deanx.randomdrop.command;

import me.deanx.randomdrop.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RandomDropCommandCompleter implements TabCompleter {
    private final List<String> COMMANDS = List.of("reload");

    public RandomDropCommandCompleter(Plugin plugin) {
        plugin.getCommand("RandomDrop").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> hint = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], COMMANDS, hint);
        }
        return hint;
    }
}
