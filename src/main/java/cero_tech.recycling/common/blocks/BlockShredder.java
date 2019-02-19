package cero_tech.recycling.common.blocks;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.ICustomModel;
import cero_tech.recycling.client.gui.GuiHandler;
import cero_tech.recycling.common.ContentRegistry;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Name: BlockShredder
 * Description: A block that recycles ItemStacks into scrap.
 * Author: cero_tech
 *
 * Last Update: 2/13/2019
 **/

public class BlockShredder extends BlockContainer implements ICustomModel {
    
    public static final PropertyBool SHREDDING = PropertyBool.create("shredding");
    
    public BlockShredder(String name) {
        super(Material.IRON);
        setRegistryName(Recycling.MOD_ID + ":" + name);
        setTranslationKey(Recycling.MOD_ID + "." + name);
        setCreativeTab(CreativeTabs.SEARCH);
        setTickRandomly(true);
        setDefaultState(blockState.getBaseState().withProperty(SHREDDING, false));

        ContentRegistry.BLOCKS.add(this);
        ContentRegistry.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
    }

    @Override
    public ModelResourceLocation getModelResourceLocation() {
        return new ModelResourceLocation(getRegistryName(), "inventory");
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(SHREDDING)) {
            // Set position variables
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double z = pos.getZ() + 0.5D;
            double offset = rand.nextDouble() * 0.6D - 0.3D;
            
            // Spawn particles
            worldIn.spawnParticle(EnumParticleTypes.CRIT, x + offset, y, z + offset, 0.0D, 0.0D, 0.0D);
            
            // Check if sound should play and additional particles should spawn
            if (rand.nextDouble() < 0.2D) {
                worldIn.playSound(x, y, x, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, rand.nextFloat(), 1.0F, false);
                worldIn.spawnParticle(EnumParticleTypes.BLOCK_DUST, x + offset, y, z + offset, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityShredder) {
            playerIn.openGui(Recycling.instance, GuiHandler.GUI_SHREDDER_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityShredder) {
                ((TileEntityShredder)te).setCustomName(stack.getDisplayName());
            }
        }
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityShredder) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityShredder)te);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityShredder();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SHREDDING);
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity te = worldIn.getTileEntity(pos);
        
        if (active) {
            worldIn.setBlockState(pos, state.withProperty(SHREDDING, true), 3);
        } else {
            worldIn.setBlockState(pos, state.withProperty(SHREDDING, false), 3);
        }
        
        if (te != null) {
            te.validate();
            worldIn.setTileEntity(pos, te);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SHREDDING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean value = meta == 1;
        return getDefaultState().withProperty(SHREDDING, value);
    }
}
