package com.slainlight.stonewall.block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BlockListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    public static Block[] blocks;
    public static Block STONE_WALL;
    public static Block COBBLESTONE_WALL;
    public static Block MOSS_STONE_WALL;
    public static Block SANDSTONE_WALL;
    public static Block BRICK_WALL;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        STONE_WALL = new WallBlock(Identifier.of(MOD_ID, "stone_wall"), Material.STONE).setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setTranslationKey(MOD_ID, "stone_wall");
        COBBLESTONE_WALL = new WallBlock(Identifier.of(MOD_ID, "cobblestone_wall"), Material.STONE).setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setTranslationKey(MOD_ID, "cobblestone_wall");
        MOSS_STONE_WALL = new WallBlock(Identifier.of(MOD_ID, "moss_stone_wall"), Material.STONE).setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setTranslationKey(MOD_ID, "moss_stone_wall");
        SANDSTONE_WALL = new WallBlock(Identifier.of(MOD_ID, "sandstone_wall"), Material.STONE).setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setTranslationKey(MOD_ID, "sandstone_wall");
        BRICK_WALL = new WallBlock(Identifier.of(MOD_ID, "brick_wall"), Material.STONE).setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setTranslationKey(MOD_ID, "brick_wall");

        blocks = new Block[]{
                BlockListener.STONE_WALL,
                BlockListener.COBBLESTONE_WALL,
                BlockListener.MOSS_STONE_WALL,
                BlockListener.SANDSTONE_WALL,
                BlockListener.BRICK_WALL,
        };
    }
}
