package cero_tech.recycling.common.tileentities;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.containers.ContainerShredder;
import cero_tech.recycling.common.ContentRegistry;
import cero_tech.recycling.common.blocks.BlockShredder;
import cero_tech.recycling.common.config.ConfigGeneral;
import cero_tech.recycling.common.recipes.RecipeRegistry;
import cero_tech.recycling.common.recipes.RecipeShredder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

/**
 * Name: TileEntityShredder
 * Description: TileEntity logic for the Shredder machine.
 * Author: cero_tech
 *
 * Last Update: 2/13/2019
 **/

public class TileEntityShredder extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyStorage {
    
    // Class constants
    private static final EnumFacing INPUT_FACE = EnumFacing.UP;
    private static final EnumFacing OUTPUT_FACE = EnumFacing.DOWN;
    
    // Class variables
    private NonNullList<ItemStack> shredderItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
    private int energyUsage = ConfigGeneral.energyUsage;
    private int totalShredTime;
    private int currentShredTime;
    private String shredderCustomName;
    
    // Capability handlers
    private IItemHandler handlerInput = new SidedInvWrapper(this, INPUT_FACE);
    private IItemHandler handlerOutput = new SidedInvWrapper(this, OUTPUT_FACE);
    private EnergyStorage handlerEnergy = new EnergyStorage(ConfigGeneral.energyCapacity);

    public static ResourceLocation getKey() {
        return new ResourceLocation(Recycling.MOD_ID + ":te_shredder");
    }
    
    public void setCustomName(String name) {
        shredderCustomName = name;
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == INPUT_FACE) {
                return (T) handlerInput;
            } else if (facing == OUTPUT_FACE) {
                return (T) handlerOutput;
            }
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) handlerEnergy;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        if (newState.getBlock() instanceof BlockShredder) {
            return false;
        }
        return super.shouldRefresh(world, pos, oldState, newState);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == INPUT_FACE ? new int[] {ContainerShredder.INPUT_SLOT_INDEX}: side == OUTPUT_FACE ? new int[] {ContainerShredder.OUTPUT_SLOT_INDEX}: new int[0];
    }
    
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn) && direction == INPUT_FACE;
    }
    
    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == ContainerShredder.OUTPUT_SLOT_INDEX && direction == OUTPUT_FACE;
    }
    
    @Override
    public int getSizeInventory() {
        return shredderItemStacks.size();
    }
    
    @Override
    public boolean isEmpty() {
        for (ItemStack stack : shredderItemStacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack getStackInSlot(int index) {
        return shredderItemStacks.get(index);
    }
    
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(shredderItemStacks, index, count);
    }
    
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(shredderItemStacks, index);
    }
    
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = shredderItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        shredderItemStacks.set(index, stack);
    
        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
    
        if (index == ContainerShredder.INPUT_SLOT_INDEX && !flag)
        {
            totalShredTime = RecipeRegistry.getShredderRecipeTime(stack);
            currentShredTime = 0;
            markDirty();
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return ConfigGeneral.maxStackSize;
    }
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
    
    @Override
    public void openInventory(EntityPlayer player) {}
    
    @Override
    public void closeInventory(EntityPlayer player) {}
    
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == ContainerShredder.OUTPUT_SLOT_INDEX) {
            return false;
        }
        return RecipeRegistry.shredderRecipeExists(stack) && (getStackInSlot(index).isItemEqual(stack) || getStackInSlot(index).isEmpty());
    }
    
    @Override
    public int getField(int id) {
        switch (id) {
            case ContainerShredder.TOTAL_TIME_INDEX: return totalShredTime;
            case ContainerShredder.CURRENT_TIME_INDEX: return currentShredTime;
            case ContainerShredder.CURRENT_ENERGY_INDEX: return handlerEnergy.getEnergyStored();
            default: return 0;
        }
    }
    
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case ContainerShredder.TOTAL_TIME_INDEX: totalShredTime = value;
            case ContainerShredder.CURRENT_TIME_INDEX: currentShredTime = value;
            case ContainerShredder.CURRENT_ENERGY_INDEX:
                handlerEnergy.extractEnergy(handlerEnergy.getEnergyStored(), false);
                handlerEnergy.receiveEnergy(value, false);
        }
    }
    
    @Override
    public int getFieldCount() {
        return 3;
    }
    
    @Override
    public void clear() {
        shredderItemStacks.clear();
        handlerEnergy.extractEnergy(handlerEnergy.getMaxEnergyStored(), false);
    }
    
    @Override
    public void update() {
        if (!getWorld().isRemote && canShred()) {
            if (!isShredding()) {
                totalShredTime = RecipeRegistry.getShredderRecipeTime(handlerInput.getStackInSlot(ContainerShredder.INPUT_SLOT_INDEX));
                currentShredTime = totalShredTime;
            }
            if (isShredding()) {
                --currentShredTime;
                handlerEnergy.extractEnergy(energyUsage, false);
                if (!isShredding()) {
                    shred();
                }
            }
            BlockShredder.setState(isShredding(), getWorld(), getPos());
        }
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerShredder(playerInventory, this);
    }
    
    @Override
    public String getGuiID() {
        return Recycling.MOD_ID + ":shredder";
    }
    
    @Override
    public String getName() {
        return hasCustomName() ? shredderCustomName: "container.shredder";
    }
    
    @Override
    public boolean hasCustomName() {
        return shredderCustomName != null && !shredderCustomName.isEmpty();
    }
    
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return handlerEnergy.receiveEnergy(maxReceive, simulate);
    }
    
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return handlerEnergy.extractEnergy(maxExtract, simulate);
    }
    
    @Override
    public int getEnergyStored() {
        return handlerEnergy.getEnergyStored();
    }
    
    @Override
    public int getMaxEnergyStored() {
        return handlerEnergy.getMaxEnergyStored();
    }
    
    @Override
    public boolean canExtract() {
        return handlerEnergy.canExtract();
    }
    
    @Override
    public boolean canReceive() {
        return handlerEnergy.canReceive();
    }
    
    private boolean isShredding() {
        return currentShredTime > 0;
    }
    
    private boolean canShred() {
        return RecipeRegistry.shredderRecipeExists(shredderItemStacks.get(ContainerShredder.INPUT_SLOT_INDEX)) &&
                handlerEnergy.getEnergyStored() > energyUsage &&
                shredderItemStacks.get(ContainerShredder.OUTPUT_SLOT_INDEX).getCount() + RecipeRegistry.getShredderRecipeScrapCount(shredderItemStacks.get(ContainerShredder.INPUT_SLOT_INDEX)) < getInventoryStackLimit();
    }
    
    private void shred() {
        if (canShred()) {
            RecipeShredder recipe = RecipeRegistry.getShredderRecipe(shredderItemStacks.get(ContainerShredder.INPUT_SLOT_INDEX));
            decrStackSize(ContainerShredder.INPUT_SLOT_INDEX, recipe.input.getCount());
            ItemStack output = new ItemStack(ContentRegistry.itemScrap, shredderItemStacks.get(ContainerShredder.OUTPUT_SLOT_INDEX).getCount() + recipe.output);
            setInventorySlotContents(ContainerShredder.OUTPUT_SLOT_INDEX, output);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        clear();

        totalShredTime = compound.getInteger("TotalShredTime");
        currentShredTime = compound.getInteger("CurrentShredTime");
        handlerEnergy.receiveEnergy(compound.getInteger("CurrentEnergy"), false);
        ItemStackHelper.loadAllItems(compound, shredderItemStacks);
    
        if (compound.hasKey("CustomName", 8)) {
            shredderCustomName = compound.getString("CustomName");
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("TotalShredTime", totalShredTime);
        compound.setInteger("CurrentShredTime", currentShredTime);
        compound.setInteger("CurrentEnergy", handlerEnergy.getEnergyStored());
        ItemStackHelper.saveAllItems(compound, shredderItemStacks);
        
        if (this.hasCustomName()) {
            compound.setString("CustomName", shredderCustomName);
        }
        
        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = writeToNBT(new NBTTagCompound());
        return new SPacketUpdateTileEntity(getPos(), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }
}
