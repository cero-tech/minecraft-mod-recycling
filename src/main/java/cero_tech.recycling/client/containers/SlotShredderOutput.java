package cero_tech.recycling.client.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Name: SlotShredderOutput
 * Description: A Slot that does not allow inserting ItemStacks.
 * Author: cero_tech
 *
 * Last Update: 2/14/2019
 **/

public class SlotShredderOutput extends SlotItemHandler {
    
    private final EntityPlayer player;
    private int removeCount;

    public SlotShredderOutput(EntityPlayer player, IItemHandler itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition);
        this.player = player;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        if (getHasStack()) {
            removeCount += Math.min(amount, getStack().getCount());
        }
    
        return super.decrStackSize(amount);
    }
    
    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }
    
    @Override
    protected void onCrafting(ItemStack stack, int amount) {
        removeCount += amount;
        onCrafting(stack);
    }
    
    @Override
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(player.world, player, removeCount);
        removeCount = 0;
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, inventory);
    }
}
