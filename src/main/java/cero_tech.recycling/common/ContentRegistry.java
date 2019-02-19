package cero_tech.recycling.common;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.ICustomModel;
import cero_tech.recycling.common.blocks.BlockShredder;
import cero_tech.recycling.common.config.ConfigGeneral;
import cero_tech.recycling.common.items.ItemScrap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

/**
 * Name: ContentRegistry
 * Description: Contains all blocks/items and registers them.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/

@Mod.EventBusSubscriber(modid = Recycling.MOD_ID)
public class ContentRegistry {
    
    // Registries
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    
    // Blocks
    public static BlockShredder blockShredder;

    // Items
    public static ItemScrap itemScrap;

    static {
        // Blocks
        blockShredder = new BlockShredder("shredder");

        // Items
        itemScrap = new ItemScrap("scrap", ConfigGeneral.maxStackSize);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Block block : BLOCKS) {
            if (block instanceof ICustomModel) {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, ((ICustomModel) block).getModelResourceLocation());
            }
        }

        for (Item item : ITEMS) {
            if (item instanceof ICustomModel) {
                ModelLoader.setCustomModelResourceLocation(item, 0, ((ICustomModel) item).getModelResourceLocation());
            }
        }
    }
}
