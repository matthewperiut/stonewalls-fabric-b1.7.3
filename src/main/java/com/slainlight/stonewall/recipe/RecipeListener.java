package com.slainlight.stonewall.recipe;

import com.slainlight.stonewall.block.BlockListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;

import java.util.Objects;

public class RecipeListener {

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);
        if (Objects.requireNonNull(type) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED)
        {
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.STONE_WALL, 6), "###", "###", '#', Block.STONE);
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.COBBLESTONE_WALL, 6), "###", "###", '#', Block.COBBLESTONE);
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.MOSS_STONE_WALL, 6), "###", "###", '#', Block.MOSSY_COBBLESTONE);
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.SANDSTONE_WALL, 6), "###", "###", '#', Block.SANDSTONE);
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.BRICK_WALL, 6), "###", "###", '#', Block.BRICKS);
        }
    }
}
