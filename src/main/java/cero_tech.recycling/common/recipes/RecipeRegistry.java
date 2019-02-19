package cero_tech.recycling.common.recipes;

import cero_tech.recycling.common.config.ConfigShredder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Name: RecipeRegistry
 * Description: Handles all of the recipes for the Shredder and Exchanger.
 * Author: cero_tech
 *
 * Last Update: 2/15/2019
 **/
public class RecipeRegistry {
    
    public static final ArrayList<RecipeShredder> SHREDDER_RECIPES = new ArrayList<>();
    
    public static void registerShredderRecipes() {
        for (String recipe : ConfigShredder.recipes) {
            String[] splitRecipe = recipe.split("-");
            if (splitRecipe.length == 3 && splitRecipe[0] != null) {
                ItemStack stack = new ItemStack(Item.getByNameOrId(splitRecipe[0]));
                int output = Integer.getInteger(splitRecipe[1]);
                int time = Integer.getInteger(splitRecipe[2]);
                addShredderRecipe(stack, output, time);
            }
        }
    }
    
    public static void addShredderRecipe(ItemStack input, int output, int time) {
        if (getShredderRecipe(input) != null) {
            SHREDDER_RECIPES.add(new RecipeShredder(input, output, time));
        }
    }
    
    public static RecipeShredder getShredderRecipe(ItemStack input) {
        for (RecipeShredder recipe : SHREDDER_RECIPES) {
            if (ItemStack.areItemStacksEqual(input, recipe.input)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static boolean shredderRecipeExists(ItemStack input) {
        return getShredderRecipe(input) != null;
    }
    
    public static int getShredderRecipeScrapCount(ItemStack input) {
        return getShredderRecipe(input).output;
    }
    
    public static int getShredderRecipeTime(ItemStack input) {
        return getShredderRecipe(input).time;
    }
}
