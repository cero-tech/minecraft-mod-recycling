package cero_tech.recycling.common.recipes;

import cero_tech.recycling.Recycling;
import cero_tech.recycling.common.config.ConfigShredder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

/**
 * Name: RecipeRegistry
 * Description: Handles all of the recipes for the Shredder and Exchanger.
 * Author: cero_tech
 *
 * Created: 2/15/2019
 **/
public class RecipeRegistry {

    // Class instance
    public static RecipeRegistry instance;

    // Array of valid shredder recipes
    private final ArrayList<RecipeShredder> SHREDDER_RECIPES = new ArrayList<>();

    public RecipeRegistry() {
        registerShredderRecipes();
    }

    private void registerShredderRecipes() {
        for (int i = 0; i < ConfigShredder.recipes.length; i++) {
            String recipe = ConfigShredder.recipes[i];
            if (!recipe.isEmpty() && recipe.contains(",")) {
                Recycling.instance.writeInfo("Adding recipe " + recipe);
                String input = recipe.substring(0, recipe.indexOf(","));
                int output = Integer.getInteger(recipe.substring(input.length() + 1, recipe.lastIndexOf(",")));
                int time = Integer.getInteger(recipe.substring(recipe.lastIndexOf(",") + 1));

                if (!input.isEmpty()) {
                    ItemStack stack = GameRegistry.makeItemStack(input, 0, 1, null);
                    addShredderRecipe(stack, output, time);
                }
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void addShredderRecipe(ItemStack input, int output, int time) {
        if (getShredderRecipe(input) != null) {
            SHREDDER_RECIPES.add(new RecipeShredder(input, output, time));
        }
    }

    public RecipeShredder getShredderRecipe(ItemStack input) {
        for (RecipeShredder recipe : SHREDDER_RECIPES) {
            if (ItemStack.areItemStacksEqual(input, recipe.getInput())) {
                return recipe;
            }
        }
        return null;
    }

    public boolean shredderRecipeExists(ItemStack input) {
        return getShredderRecipe(input) != null;
    }
}
