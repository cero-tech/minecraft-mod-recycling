package cero_tech.recycling.common.recipes;

import net.minecraft.item.ItemStack;

/**
 * Name: RecipeShredder
 * Description: Data storage for Shredder recipes.
 * Author: cero_tech
 *
 * Created: 2/15/2019
 **/
public class RecipeShredder {

    private final ItemStack input;
    private final int output;
    private final int time;
    
    public RecipeShredder(ItemStack input, int output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public ItemStack getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }
}
