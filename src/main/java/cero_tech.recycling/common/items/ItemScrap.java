package cero_tech.recycling.common.items;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.ICustomModel;
import cero_tech.recycling.common.ContentRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Name: ItemScrap
 * Description: Simple item gained from a Shredder that can be used to obtain other materials via an Exchanger.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/

public class ItemScrap extends Item implements ICustomModel {
    
    public ItemScrap(String name) {
        setRegistryName(Recycling.MOD_ID + ":" + name);
        setTranslationKey(Recycling.MOD_ID + "." + name);
        setCreativeTab(CreativeTabs.SEARCH);
        // TODO: setMaxStackSize(INVENTORY_STACK_LIMIT);
        ContentRegistry.ITEMS.add(this);
    }
    
    @Override
    public ModelResourceLocation getModelResourceLocation() {
        return new ModelResourceLocation(getRegistryName(), "inventory");
    }
}
