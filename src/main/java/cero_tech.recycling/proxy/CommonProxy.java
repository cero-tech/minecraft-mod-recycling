package cero_tech.recycling.proxy;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.client.gui.GuiHandler;
import cero_tech.recycling.common.recipes.RecipeRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Name: CommonProxy
 * Description: Server side initialization and event handling.
 * Author: cero_tech
 *
 * Created: 2/13/2019
 **/

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Recycling.instance.writeInfo("Registering GUI handler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(Recycling.instance, new GuiHandler());
        Recycling.instance.writeInfo("Finished registering GUI handler");
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        Recycling.instance.writeInfo("Registering custom recipes...");
        RecipeRegistry.instance = new RecipeRegistry();
        Recycling.instance.writeInfo("Finished registering custom recipes");
    }
}
