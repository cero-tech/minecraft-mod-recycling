package cero_tech.recycling.common.recipes;

import net.minecraft.item.ItemStack;

/**
 * Name: RecipeShredder
 * Description:
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/
public class RecipeShredder {
    
    public final ItemStack input;
    public final int output;
    public final int time;
    
    public RecipeShredder(ItemStack input, int output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }
}
