package cero_tech.recycling.client.containers;

import cero_tech.recycling.common.recipes.RecipeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Name: ContainerShredder
 * Description: The logic for the Shredder's inventory.
 * Author: cero_tech
 *
 * Last Update: 2/14/2019
 **/

public class ContainerShredder extends Container {
    
    public static final int INPUT_SLOT_INDEX = 0;
    public static final int OUTPUT_SLOT_INDEX = 1;
    public static final int TOTAL_TIME_INDEX = 0;
    public static final int CURRENT_TIME_INDEX = 1;
    public static final int CURRENT_ENERGY_INDEX = 2;
    
    private static final int[] INPUT_POS = {56, 35};
    private static final int[] OUTPUT_POS = {112, 31};
    
    private final IInventory tileShredder;
    private int totalShredTime;
    private int currentShredTime;
    private int currentEnergy;
    
    public ContainerShredder(InventoryPlayer playerInventory, IInventory shredderInventory) {
        this.tileShredder = shredderInventory;
        addSlotToContainer(new Slot(shredderInventory, INPUT_SLOT_INDEX, INPUT_POS[0], INPUT_POS[1]));
        addSlotToContainer(new SlotShredderOutput(playerInventory.player, shredderInventory, OUTPUT_SLOT_INDEX, OUTPUT_POS[0], OUTPUT_POS[1]));
        addPlayerInventorySlots(playerInventory);
    }
    
    private void addPlayerInventorySlots(InventoryPlayer playerInventory) {
        // Add the 27 (3 rows of 9) player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        // Add the 9 hotbar slots
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileShredder.isUsableByPlayer(playerIn);
    }
    
    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileShredder);
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
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack0 = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
    
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack0 = stack1.copy();
    
            if (index == OUTPUT_SLOT_INDEX) {
                if (!mergeItemStack(stack1, OUTPUT_SLOT_INDEX + 1, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack1, stack0);
            } else if (index > OUTPUT_SLOT_INDEX) {
                if (!RecipeRegistry.shredderRecipeExists(stack1) || !mergeItemStack(stack1, INPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX, false)) {
                    return ItemStack.EMPTY;
                }
            }
    
            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
    
            if (stack1.getCount() == stack0.getCount()) {
                return ItemStack.EMPTY;
            }
    
            slot.onTake(playerIn, stack1);
        }
        
        return stack0;
    }
}
