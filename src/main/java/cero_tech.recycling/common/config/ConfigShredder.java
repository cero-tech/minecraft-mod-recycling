package cero_tech.recycling.common.config;

import cero_tech.recycling.Recycling;
import net.minecraftforge.common.config.Config;

/**
 * Name: ConfigShredder
 * Description: Contains all customizable recipes for the Shredder.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/

@Config(modid = Recycling.MOD_ID, name = Recycling.MOD_NAME + "-Shredder")
public class ConfigShredder {
    
    @Config.Name("Recipes")
    @Config.Comment("All recipes should be stored as a string in the format 'modid:name-output-ticks'.")
    public static String[] recipes = {
            "minecraft:activator_rail-1-100",
            "minecraft:anvil-3-100",
            "minecraft:beacon-4-100",
            "minecraft:black_concrete-1-100",
            "minecraft:black_concrete_powder-1-100",
            "minecraft:black_shulker_box-1-100",
            "minecraft:blue_concrete-1-100",
            "minecraft:blue_concrete_powder-1-100",
            "minecraft:blue_shulker_box-1-100",
            "minecraft:bookshelf-1-100",
            "minecraft:brewing_stand-1-100",
            "minecraft:brown_concrete-1-100",
            "minecraft:brown_concrete_powder-1-100",
            "minecraft:brown_shulker_box-1-100",
            "minecraft:cauldron-2-100",
            "minecraft:cyan_concrete-1-100",
            "minecraft:cyan_concrete_powder-1-100",
            "minecraft:cyan_shulker_box-1-100",
            "minecraft:daylight_detector-1-100",
            "minecraft:daylight_detector_inverted-1-100",
            "minecraft:detector_rail-1-100",
            "minecraft:dispenser-1-100",
            "minecraft:dragon_egg-10-100",
            "minecraft:dropper-1-100",
            "minecraft:enchanting_table-3-100",
            "minecraft:ender_chest-3-100",
            "minecraft:end_rod-1-100",
            "minecraft:golden_rail-1-100",
            "minecraft:gray_concrete-1-100",
            "minecraft:gray_concrete_powder-1-100",
            "minecraft:gray_shulker_box-1-100",
            "minecraft:green_concrete-1-100",
            "minecraft:green_concrete_powder-1-100",
            "minecraft:green_shulker_box-1-100",
            "minecraft:heavy_weighted_pressure_plate-1-100",
            "minecraft:hopper-2-100",
            "minecraft:iron_bars-1-100",
            "minecraft:iron_door-2-100",
            "minecraft:iron_trapdoor-1-100",
            "minecraft:jukebox-1-100",
            "minecraft:light_blue_concrete-1-100",
            "minecraft:light_blue_concrete_powder-1-100",
            "minecraft:light_blue_shulker_box-1-100",
            "minecraft:light_weighted_pressure_plate-1-100",
            "minecraft:lime_concrete-1-100",
            "minecraft:lime_concrete_powder-1-100",
            "minecraft:lime_shulker_box-1-100",
            "minecraft:magenta_concrete-1-100",
            "minecraft:magenta_concrete_powder-1-100",
            "minecraft:magenta_shulker_box-1-100",
            "minecraft:noteblock-1-100",
            "minecraft:observer-2-100",
            "minecraft:orange_concrete-1-100",
            "minecraft:orange_concrete_powder-1-100",
            "minecraft:orange_shulker_box-1-100",
            "minecraft:pink_concrete-1-100",
            "minecraft:pink_concrete_powder-1-100",
            "minecraft:pink_shulker_box-1-100",
            "minecraft:piston-2-100",
            "minecraft:powered_comparator-1-100",
            "minecraft:powered_repeater-1-100",
            "minecraft:purple_concrete-1-100",
            "minecraft:purple_concrete_powder-1-100",
            "minecraft:purple_shulker_box-1-100",
            "minecraft:rail-1-100",
            "minecraft:red_concrete-1-100",
            "minecraft:red_concrete_powder-1-100",
            "minecraft:red_shulker_box-1-100",
            "minecraft:redstone_lamp-2-100",
            "minecraft:-1-100",
            "minecraft:silver_concrete-1-100",
            "minecraft:silver_concrete_powder-1-100",
            "minecraft:silver_shulker_box-1-100",
            "minecraft:sticky_piston-2-100",
            "minecraft:tnt-1-100",
            "minecraft:unpowered_comparator-1-100",
            "minecraft:unpowered_repeater-1-100",
            "minecraft:white_concrete-1-100",
            "minecraft:white_concrete_powder-1-100",
            "minecraft:white_shulker_box-1-100",
            "minecraft:yellow_concrete-1-100",
            "minecraft:yellow_concrete_powder-1-100",
            "minecraft:yellow_shulker_box-1-100"
    };
}
