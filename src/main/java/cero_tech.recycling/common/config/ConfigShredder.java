package cero_tech.recycling.common.config;

import cero_tech.recycling.Recycling;
import net.minecraftforge.common.config.Config;

/**
 * Name: ConfigShredder
 * Description: Contains all configurable variables for the Shredder and customizable recipes.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/

@Config(modid = Recycling.MOD_ID, name = Recycling.MOD_NAME + "-shredder")
public class ConfigShredder {
    
    @Config.Name("Energy Capacity")
    @Config.RangeInt(min = 1)
    public static final int energyCapacity = 10000;
    
    @Config.Name("Energy Usage")
    @Config.RangeInt(min = 0)
    public static final int energyUsage = 100;
    
    @Config.Name("Max Stack Size")
    @Config.RangeInt(min = 1)
    public static final int maxStackSize = 64;
    
    @Config.Name("Recipes")
    @Config.Comment("")
    public static final String[] recipes = {
            // TODO
            "minecraft:furnace-1-100"
    };
}
