package cero_tech.recycling.client.gui;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.containers.ContainerShredder;
import cero_tech.recycling.common.config.ConfigGeneral;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Name: GuiShredder
 * Description: Handles all rendering for the Shredder GUI.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/

@SideOnly(Side.CLIENT)
public class GuiShredder extends GuiContainer {
    
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Recycling.MOD_ID + ":textures/gui/shredder.png");
    private static final int TEXT_COLOR = 4210752;
    private static final int PROGRESS_HEIGHT = 24;
    private static final int[] PROGRESS_POS = {79, 34};
    private static final int[] PROGRESS_OVERLAY = {176, 1};
    private static final int ENERGY_HEIGHT = 64;
    private static final int ENERGY_WIDTH = 6;
    private static final int[] ENERGY_POS = {163, 8};
    private static final int[] ENERGY_OVERLAY = {176, 18};
    
    private final InventoryPlayer playerInventory;
    private final TileEntityShredder tileShredder;

    public GuiShredder(InventoryPlayer playerInventory, TileEntityShredder tileShredder) {
        super(new ContainerShredder(playerInventory, tileShredder));
        this.playerInventory = playerInventory;
        this.tileShredder = tileShredder;
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = tileShredder.getDisplayName().getUnformattedText();
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, TEXT_COLOR);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, TEXT_COLOR);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawProgress(x + PROGRESS_POS[0], y + PROGRESS_POS[1]);
        drawEnergy(x + ENERGY_POS[0], y + ENERGY_POS[1]);
    }
    
    private void drawProgress(int x, int y) {
        int current = tileShredder.getField(ContainerShredder.CURRENT_TIME_INDEX);
        int total = tileShredder.getField(ContainerShredder.TOTAL_TIME_INDEX);
        int progress = current != 0 && total != 0 ? current * PROGRESS_HEIGHT / total : 0;
        drawTexturedModalRect(x, y, PROGRESS_OVERLAY[0], PROGRESS_OVERLAY[1], progress + 1, PROGRESS_HEIGHT);
    }
    
    private void drawEnergy(int x, int y) {
        int current = tileShredder.getField(ContainerShredder.CURRENT_ENERGY_INDEX);
        int total = ConfigGeneral.energyCapacity;
        int energy = current != 0 ? current * ENERGY_HEIGHT / total : 0;
        drawTexturedModalRect(x, y - energy, ENERGY_OVERLAY[0], ENERGY_OVERLAY[1] - energy, ENERGY_WIDTH, energy + 1);
    }
}
