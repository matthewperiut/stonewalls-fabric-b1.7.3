package com.slainlight.stonewall.recipe;

import com.slainlight.stonewall.block.BlockListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;

import java.util.Objects;

public class RecipeListener {

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);
        if (Objects.requireNonNull(type) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED)
        {
            CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.STONE_WALL, 6), "###", "###", '#', BlockBase.STONE);
            CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.COBBLESTONE_WALL, 6), "###", "###", '#', BlockBase.COBBLESTONE);
            CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.MOSS_STONE_WALL, 6), "###", "###", '#', BlockBase.MOSSY_COBBLESTONE);
            CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.SANDSTONE_WALL, 6), "###", "###", '#', BlockBase.SANDSTONE);
            CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.BRICK_WALL, 6), "###", "###", '#', BlockBase.BRICKS);
        }
    }
}
