package cero_tech.recycling.common.containers;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.gui.GuiHandler.GUI;
import cero_tech.recycling.common.recipes.RecipeRegistry;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Name: ContainerShredder
 * Description: The logic for the Shredder's inventory.
 * Author: cero_tech
 *
 * Created: 2/14/2019
 **/

public class ContainerShredder extends Container {

    // Global constants
    public static final int TOTAL_TIME_INDEX = 0;
    public static final int CURRENT_TIME_INDEX = 1;
    public static final int CURRENT_ENERGY_INDEX = 2;

    // Class variables
    private final InventoryPlayer playerInventory;
    private final TileEntityShredder tileShredder;
    private int totalShredTime;
    private int currentShredTime;
    private int currentEnergy;

    public ContainerShredder(InventoryPlayer playerInventory, final TileEntityShredder tileShredder) {
        this.playerInventory = playerInventory;
        this.tileShredder = tileShredder;
        addSlots();
        addPlayerInventorySlots();
    }

    private void addSlots() {
        Recycling.instance.writeInfo("Adding shredder inventory slots...");
        IItemHandler input = tileShredder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler output = tileShredder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        addSlotToContainer(new SlotItemHandler(input, GUI.SHREDDER.getInputIndex(), GUI.SHREDDER.getInputUV()[0], GUI.SHREDDER.getInputUV()[1]));
        addSlotToContainer(new SlotShredderOutput(playerInventory.player, output, GUI.SHREDDER.getOutputIndex(), GUI.SHREDDER.getOutputUV()[0], GUI.SHREDDER.getOutputUV()[1]));
        Recycling.instance.writeInfo("Finished adding shredder inventory slots");
    }

    private void addPlayerInventorySlots() {
        Recycling.instance.writeInfo("Adding player inventory slots...");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Recycling.instance.writeInfo("Current index: " + (j + ((i + 1) * 9)));
                addSlotToContainer(new Slot(playerInventory, j + ((i + 1) * 9), 8 + j * 18, 84 + i * 18));
            }
        }
        Recycling.instance.writeInfo("Finished adding player inventory slots");

        Recycling.instance.writeInfo("Adding player hotbar slots...");
        for (int k = 0; k < 9; k++) {
            Recycling.instance.writeInfo("Current index: " + k);
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
        Recycling.instance.writeInfo("Finished adding hotbar inventory slots");
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileShredder);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return tileShredder.isUsableByPlayer(playerIn);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for (IContainerListener listener : listeners) {
            if (totalShredTime != tileShredder.getField(TOTAL_TIME_INDEX)) {
                listener.sendWindowProperty(this, TOTAL_TIME_INDEX, tileShredder.getField(TOTAL_TIME_INDEX));
            }
            if (currentShredTime != tileShredder.getField(CURRENT_TIME_INDEX)) {
                listener.sendWindowProperty(this, CURRENT_TIME_INDEX, tileShredder.getField(CURRENT_TIME_INDEX));
            }
            if (currentEnergy != tileShredder.getField(CURRENT_ENERGY_INDEX)) {
                listener.sendWindowProperty(this, CURRENT_ENERGY_INDEX, tileShredder.getField(CURRENT_ENERGY_INDEX));
            }
        }
        
        totalShredTime = tileShredder.getField(TOTAL_TIME_INDEX);
        currentShredTime = tileShredder.getField(CURRENT_TIME_INDEX);
        currentEnergy = tileShredder.getField(CURRENT_ENERGY_INDEX);
    }
    
    @Override
    public void updateProgressBar(int id, int data) {
        tileShredder.setField(id, data);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack0 = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack0 = stack1.copy();

            if (index == GUI.SHREDDER.getOutputIndex()) {
                if (!mergeItemStack(stack1, GUI.SHREDDER.getOutputIndex() + 1, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack1, stack0);
            } else if (index != GUI.SHREDDER.getInputIndex()) {
                if (RecipeRegistry.instance.getShredderRecipe(stack1).getOutput() != 0) {
                    if (!mergeItemStack(stack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= GUI.SHREDDER.getOutputIndex() + 1 && index < GUI.SHREDDER.getOutputIndex() + 27) {
                    if (!mergeItemStack(stack1, GUI.SHREDDER.getOutputIndex() + 27, inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= GUI.SHREDDER.getOutputIndex() + 27 && index < inventorySlots.size() && !mergeItemStack(stack1, GUI.SHREDDER.getOutputIndex() + 1, GUI.SHREDDER.getOutputIndex() + 27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stack1, GUI.SHREDDER.getOutputIndex() + 1, inventorySlots.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack1.getCount() == stack0.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack1);
        }
        return stack0;
    }

    @Override
    public void onContainerClosed(@Nonnull EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        tileShredder.closeInventory(playerIn);
        Recycling.instance.writeInfo(tileShredder.getGuiID() + " container closed");
    }
}
