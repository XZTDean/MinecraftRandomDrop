package me.deanx.randomdrop;

import me.deanx.randomdrop.command.RandomDropCommand;
import me.deanx.randomdrop.command.RandomDropCommandCompleter;
import me.deanx.randomdrop.listener.BlockItemDrop;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    private BlockItemDrop listener;

    @Override
    public void onEnable() {
        new RandomDropCommand(this);
        new RandomDropCommandCompleter(this);
        listener = new BlockItemDrop(this);
    }

    public void reload() {
        listener.unregister();
        listener = new BlockItemDrop(this);
    }
}
