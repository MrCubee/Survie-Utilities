package fr.mrcubee.su.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final BlockFace[] blockFaces;

    public BlockBreakListener() {
        final BlockFace[] values = BlockFace.values();

        this.blockFaces = new BlockFace[values.length - 1];
        for (int i = 0, remove = 0; i < values.length; i++) {
            if (values[i] != BlockFace.SELF)
                this.blockFaces[i - remove] = values[i];
            else
                remove++;
        }
    }

    public short breakTree(Block block, short durability, int limit, ItemStack itemStack) {
        Block nextBlock;
        boolean isLeaves;

        if (limit < 0 || durability <= 0
        || (!(isLeaves = block.getType().toString().toLowerCase().contains("leaves"))
        && !block.getType().toString().toLowerCase().contains("log")))
            return durability;
        block.breakNaturally(itemStack);
        if (!isLeaves)
            durability--;
        for (BlockFace blockFace : this.blockFaces) {
            nextBlock = block.getRelative(blockFace);
            durability = breakTree(nextBlock, durability, limit - 1, itemStack);
            if (durability <= 0)
                return durability;
        }
        return durability;
    }

    @EventHandler
    public void event(BlockBreakEvent event) {
        final ItemStack itemStack = event.getPlayer().getItemInHand();
        final Block block = event.getBlock();
        final short durability;

        if (itemStack == null || !itemStack.getType().toString().toLowerCase().contains("axe"))
            return;
        if (!block.getType().toString().toLowerCase().contains("log")
        && !block.getType().toString().toLowerCase().contains("leaves"))
            return;
        event.setCancelled(true);
        durability = breakTree(block, (short) (itemStack.getType().getMaxDurability() - itemStack.getDurability()), 150, itemStack);
        if (durability <= 0)
            event.getPlayer().setItemInHand(null);
        else
            itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - durability));
    }
    
}
