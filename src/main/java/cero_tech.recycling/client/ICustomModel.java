package cero_tech.recycling.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Name: ICustomModel
 * Description: This interface adds a single function to retrieve an item/block model resource location.
 * Author: cero_tech
 *
 * Last Update: 2/13/2019
 **/

public interface ICustomModel {
    @SideOnly(Side.CLIENT)
    ModelResourceLocation getModelResourceLocation();
}
