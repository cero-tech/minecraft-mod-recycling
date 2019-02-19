package cero_tech.recycling.common.config;

import cero_tech.recycling.Recycling;
import net.minecraftforge.common.config.Config;

/**
 * Name: ConfigGeneral
 * Description: Contains all general configuration variable.
 * Author: cero_tech
 * <p>
 * Last Update: 2/19/2019
 **/

@Config(modid = Recycling.MOD_ID, name = Recycling.MOD_NAME)
public class ConfigGeneral {

    @Config.Name("Max Stack Size")
    @Config.RangeInt(min = 1)
    public static Integer maxStackSize = 64;

    @Config.Name("Machine Energy Capacity")
    @Config.RangeInt(min = 1)
    public static Integer energyCapacity = 10000;

    @Config.Name("Machine Energy Usage")
    @Config.RangeInt(min = 0)
    public static Integer energyUsage = 100;
}
