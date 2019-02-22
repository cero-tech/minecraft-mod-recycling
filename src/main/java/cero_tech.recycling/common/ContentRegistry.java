package cero_tech.recycling.common;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.ICustomModel;
import cero_tech.recycling.common.blocks.BlockShredder;
import cero_tech.recycling.common.config.ConfigGeneral;
import cero_tech.recycling.common.items.ItemScrap;
import cero_tech.recycling.common.tileentities.TileEntityShredder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

/**
 * Name: ContentRegistry
 * Description: Contains all blocks/items and registers them.
 * Author: cero_tech
 *
 * Created: 2/15/2019
 **/

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = Recycling.MOD_ID)
public class ContentRegistry {
    
    // Registries
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    
    // Blocks
    public static final BlockShredder blockShredder;

    // Items
    public static final ItemScrap itemScrap;

    static {
        // Blocks
        blockShredder = new BlockShredder("shredder");

        // Items
        itemScrap = new ItemScrap("scrap", ConfigGeneral.maxStackSize);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Recycling.instance.writeInfo("Registering Blocks...");
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        Recycling.instance.writeInfo("Finished Registering Blocks");

        Recycling.instance.writeInfo("Registering Tile Entities...");
        GameRegistry.registerTileEntity(TileEntityShredder.class, TileEntityShredder.getKey());
        Recycling.instance.writeInfo("Finished Registering Tile Entities");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Recycling.instance.writeInfo("Registering Items...");
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        Recycling.instance.writeInfo("Finished Registering Items");
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Recycling.instance.writeInfo("Registering Block Models...");
        for (Block block : BLOCKS) {
            if (block instanceof ICustomModel) {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, ((ICustomModel) block).getModelResourceLocation());
            }
        }
        Recycling.instance.writeInfo("Finished Registering Block Models");

        Recycling.instance.writeInfo("Registering Item Models...");
        for (Item item : ITEMS) {
            if (item instanceof ICustomModel) {
                ModelLoader.setCustomModelResourceLocation(item, 0, ((ICustomModel) item).getModelResourceLocation());
            }
        }
        Recycling.instance.writeInfo("Finished Registering Item Models");
    }
}
