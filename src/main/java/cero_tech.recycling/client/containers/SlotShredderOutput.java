package cero_tech.recycling.client.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Name: SlotShredderOutput
 * Description: An extension of a Slot that does not allow inserting ItemStacks.
 * Author: cero_tech
 *
 * Last Update: 2/14/2019
 **/

public class SlotShredderOutput extends Slot {
    
    private final EntityPlayer player;
    private int removeCount;
    
    public SlotShredderOutput(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
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
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        removeCount = 0;
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, inventory);
    }
}
