package me.deanx.randomdrop;

import me.deanx.randomdrop.listener.BlockItemDrop;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new BlockItemDrop(this);
    }
}