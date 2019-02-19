package cero_tech.recycling.client.gui;

import cero_tech.recycling.client.containers.ContainerShredder;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * Name: GuiHandler
 * Description: Handles GUI/Container implementation
 * Author: cero_tech
 * <p>
 * Last Update: 2/19/2019
 **/

public class GuiHandler implements IGuiHandler {

    public static final int GUI_SHREDDER_ID = 0;
    public static final int GUI_EXCHANGER_ID = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_SHREDDER_ID:
                TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                if (!(te instanceof TileEntityShredder)) return null;
                return new ContainerShredder(player.inventory, (TileEntityShredder) te);
            case GUI_EXCHANGER_ID:
                return null;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_SHREDDER_ID:
                TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                if (!(te instanceof TileEntityShredder)) return null;
                return new GuiShredder(player.inventory, (TileEntityShredder) te);
            case GUI_EXCHANGER_ID:
                return null;
            default:
                return null;
        }
    }
}
