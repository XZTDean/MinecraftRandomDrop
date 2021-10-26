package me.deanx.randomdrop.listener;

import me.deanx.randomdrop.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class BlockItemDrop implements Listener {
    private final Random random = new Random();
    private final HashSet<Material> unavailableItem = new HashSet<>(List.of(
            Material.COMMAND_BLOCK, Material.COMMAND_BLOCK_MINECART, Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.AIR));


    private static final Material[] materials = Material.values();
    private final HashMap<Player, HashMap<Material, Material>> userDropMap = new HashMap<>();

    public BlockItemDrop(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onBlockItemDrop(BlockDropItemEvent event) {
        Player player = event.getPlayer();

        if (!userDropMap.containsKey(player))
            userDropMap.put(player, new HashMap<>());

        HashMap<Material, Material> dropMap = userDropMap.get(player);

        List<Item> itemList = event.getItems();
        for (Item item : itemList) {
            ItemStack itemStack = item.getItemStack();
            Material type = itemStack.getType();
            if (!dropMap.containsKey(type)) {
                Material material;
                do {
                    material = materials[random.nextInt(materials.length)];
                } while (!material.isItem() || unavailableItem.contains(material) || isDeprecated(material));
                dropMap.put(type, material);
                unavailableItem.add(material);
            }
            ItemStack newItem = new ItemStack(dropMap.get(type), itemStack.getAmount());
            item.setItemStack(newItem);
        }
    }

    private boolean isDeprecated(Material material) {
        Field field;
        try {
            field = Material.class.getField(material.name());
        } catch (NoSuchFieldException e) {
            Bukkit.getLogger().warning("Find unknown Material " + material.name());
            return true;
        }
        if (field.isAnnotationPresent(Deprecated.class)) {
            unavailableItem.add(material);
            return true;
        } else {
            return false;
        }
    }
}
