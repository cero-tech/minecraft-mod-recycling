package cero_tech.recycling.client.gui;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.common.containers.ContainerShredder;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * Name: GuiHandler
 * Description: Handles GUI/Container implementation
 * Author: cero_tech
 *
 * Created: 2/19/2019
 **/

public class GuiHandler implements IGuiHandler {

    private static final int SHREDDER_GUI_ID = 0;
    private static final int EXCHANGER_GUI_ID = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Recycling.instance.writeInfo("Obtaining container...");
        if (ID == SHREDDER_GUI_ID) {
            Recycling.instance.writeInfo("Obtained container with ID " + ID);
            return new ContainerShredder(player.inventory, (TileEntityShredder) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (ID == EXCHANGER_GUI_ID) {
            Recycling.instance.writeInfo("Obtained container with ID " + ID);
            return null;
        }
        Recycling.instance.writeError("Failed to obtain container with ID " + ID);
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Recycling.instance.writeInfo("Obtaining GUI...");
        if (ID == SHREDDER_GUI_ID) {
            Recycling.instance.writeInfo("Obtained GUI with ID " + ID);
            return new GuiShredder(player.inventory, (TileEntityShredder) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (ID == EXCHANGER_GUI_ID) {
            Recycling.instance.writeInfo("Obtained GUI with ID " + ID);
            return null;
        }
        Recycling.instance.writeError("Failed to obtain container with ID " + ID);
        return null;
    }

    public enum GUI {

        SHREDDER(
                new int[]{56, 35},
                new int[]{112, 31},
                new int[]{174, 164},
                new ResourceLocation(Recycling.MOD_ID, "textures/gui/shredder.png")
        ),
        EXCHANGER(
                new int[]{56, 35},
                new int[]{112, 31},
                new int[]{174, 164},
                new ResourceLocation(Recycling.MOD_ID, "textures/gui/exchanger.png")
        );

        private final int inputIndex, outputIndex;
        private final int[] inputUV, outputUV, guiSize;
        private final ResourceLocation textureLocation;

        GUI(int[] inputUV, int[] outputUV, int[] guiSize, ResourceLocation textureLocation) {
            this.inputIndex = 0;
            this.outputIndex = 1;
            this.inputUV = inputUV;
            this.outputUV = outputUV;
            this.guiSize = guiSize;
            this.textureLocation = textureLocation;
        }

        public int getInputIndex() {
            return inputIndex;
        }

        public int getOutputIndex() {
            return outputIndex;
        }

        public int[] getInputUV() {
            return inputUV;
        }

        public int[] getOutputUV() {
            return outputUV;
        }

        public int[] getGuiSize() {
            return guiSize;
        }

        public ResourceLocation getTextureLocation() {
            Recycling.instance.writeInfo("Getting texture from " + textureLocation.toString());
            return textureLocation;
        }
    }
}
