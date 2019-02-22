package cero_tech.recycling.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Name: SlotShredderOutput
 * Description: A Slot that does not allow inserting ItemStacks.
 * Author: cero_tech
 *
 * Created: 2/14/2019
 **/

@SuppressWarnings("WeakerAccess")
public class SlotShredderOutput extends SlotItemHandler {
    
    private final EntityPlayer player;
    private int removeCount;

    public SlotShredderOutput(EntityPlayer player, IItemHandler itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition);
        this.player = player;
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int amount) {
        if (getHasStack()) {
            removeCount += Math.min(amount, getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Nonnull
    @Override
    public ItemStack onTake(EntityPlayer thePlayer, @Nonnull ItemStack stack) {
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
