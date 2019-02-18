package cero_tech.recycling.common;

import cero_tech.recycling.common.blocks.BlockShredder;
import cero_tech.recycling.common.items.ItemScrap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * Name: ContentRegistry
 * Description:
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/
public class ContentRegistry {
    
    // Registries
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    
    // Blocks
    public static BlockShredder blockShredder = new BlockShredder("shredder");
    
    // Items
    public static ItemScrap itemScrap = new ItemScrap("scrap");
}
