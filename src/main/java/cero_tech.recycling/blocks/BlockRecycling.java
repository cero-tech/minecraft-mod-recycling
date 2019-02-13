package cero_tech.recycling.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 *
 * Name: BlockRecycling
 * Description: Provides a base for all Recycling mod blocks.
 * Author: cero_tech
 *
 * Last Update: 2/13/2019
 *
 **/

public class BlockRecycling extends Block implements ICustomModel {
    
    public BlockRecycling(Material blockMaterialIn) {
        super(blockMaterialIn);
    }
    
    @Override
    public ModelResourceLocation getModelResourceLocation() {
        return null;
    }
}
