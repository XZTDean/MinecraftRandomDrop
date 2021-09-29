package me.deanx.randomdrop.listener;

import me.deanx.randomdrop.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BlockItemDrop implements Listener {
    private final HashMap<Material, Material> dropMap = new HashMap<>();
    private static final Material[] materials = Material.values();
    private final Random random = new Random();

    public BlockItemDrop(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockItemDrop(BlockDropItemEvent event) {
        List<Item> itemList = event.getItems();
        for (Item item : itemList) {
            ItemStack itemStack = item.getItemStack();
            Material type = itemStack.getType();
            if (!dropMap.containsKey(type)) {
                Material material;
                do {
                    material = materials[random.nextInt(materials.length)];
                } while (!material.isItem() || dropMap.containsValue(material));
                dropMap.put(type, material);
            }
            ItemStack newItem = new ItemStack(dropMap.get(type), itemStack.getAmount());
            item.setItemStack(newItem);
        }
    }
}
