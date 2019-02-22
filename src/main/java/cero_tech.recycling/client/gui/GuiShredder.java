package cero_tech.recycling.client.gui;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.common.config.ConfigGeneral;
import cero_tech.recycling.common.containers.ContainerShredder;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

/**
 * Name: GuiShredder
 * Description: Handles all rendering for the Shredder GUI.
 * Author: cero_tech
 *
 * Created: 2/15/2019
 **/

@SuppressWarnings("WeakerAccess")
public class GuiShredder extends GuiContainer {

    // Class constants
    private static final int TEXT_COLOR = 4210752;
    private static final int PROGRESS_HEIGHT = 24;
    private static final int[] PROGRESS_POS = {79, 34};
    private static final int[] PROGRESS_OVERLAY = {176, 1};
    private static final int ENERGY_HEIGHT = 64;
    private static final int ENERGY_WIDTH = 6;
    private static final int[] ENERGY_POS = {163, 71};
    private static final int[] ENERGY_OVERLAY = {176, 81};

    private final InventoryPlayer playerInventory;
    private final TileEntityShredder tileShredder;

    public GuiShredder(InventoryPlayer playerInventory, TileEntityShredder tileShredder) {
        super(new ContainerShredder(playerInventory, tileShredder));
        xSize = GuiHandler.GUI.SHREDDER.getGuiSize()[0];
        ySize = GuiHandler.GUI.SHREDDER.getGuiSize()[1];
        this.playerInventory = playerInventory;
        this.tileShredder = tileShredder;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        inventorySlots.onContainerClosed(playerInventory.player);
        Recycling.instance.writeInfo("GUI with ID " + tileShredder.getGuiID() + " closed");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Recycling.instance.writeInfo("Drawing background layer for " + tileShredder.getGuiID() + "...");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiHandler.GUI.SHREDDER.getTextureLocation());

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawProgress();
        drawEnergy();
        Recycling.instance.writeInfo("Finished drawing background layer for " + tileShredder.getGuiID());
    }

    @SuppressWarnings("null")
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Recycling.instance.writeInfo("Drawing foreground layer for " + tileShredder.getGuiID() + "...");
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        String title = I18n.format(Objects.requireNonNull(tileShredder.getDisplayName()).getUnformattedText());
        int x = (xSize / 2) - (fontRenderer.getStringWidth(title) / 2);
        int y = 6;
        fontRenderer.drawString(title, x, y, TEXT_COLOR);
        Recycling.instance.writeInfo("Finished drawing foreground layer for " + tileShredder.getGuiID());
    }

    private void drawProgress() {
        int x = (width - xSize / 2) + PROGRESS_POS[0];
        int y = (height - ySize / 2) + PROGRESS_POS[1];
        int current = tileShredder.getField(ContainerShredder.CURRENT_TIME_INDEX);
        int total = tileShredder.getField(ContainerShredder.TOTAL_TIME_INDEX);
        int progress = current * PROGRESS_HEIGHT / total;
        drawTexturedModalRect(x, y, PROGRESS_OVERLAY[0], PROGRESS_OVERLAY[1], progress + 1, PROGRESS_HEIGHT);
    }

    private void drawEnergy() {
        int x = (width - xSize / 2) + ENERGY_POS[0];
        int y = (height - ySize / 2) + ENERGY_POS[1];
        int current = tileShredder.getField(ContainerShredder.CURRENT_ENERGY_INDEX);
        int total = ConfigGeneral.energyCapacity;
        int energy = current * ENERGY_HEIGHT / total;
        drawTexturedModalRect(x, y - energy, ENERGY_OVERLAY[0], ENERGY_OVERLAY[1] - energy, ENERGY_WIDTH, energy + 1);
    }
}
