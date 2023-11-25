package com.slainlight.stonewall.block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BlockListener {
    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();

    public static BlockBase STONE_WALL;
    public static BlockBase COBBLESTONE_WALL;
    public static BlockBase MOSS_STONE_WALL;
    public static BlockBase SANDSTONE_WALL;
    public static BlockBase BRICK_WALL;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        STONE_WALL = new WallBlock(Identifier.of(MOD_ID, "stone_wall"), Material.STONE).setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.PISTON_SOUNDS).setTranslationKey(MOD_ID, "stone_wall");
        COBBLESTONE_WALL = new WallBlock(Identifier.of(MOD_ID, "cobblestone_wall"), Material.STONE).setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.PISTON_SOUNDS).setTranslationKey(MOD_ID, "cobblestone_wall");
        MOSS_STONE_WALL = new WallBlock(Identifier.of(MOD_ID, "moss_stone_wall"), Material.STONE).setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.PISTON_SOUNDS).setTranslationKey(MOD_ID, "moss_stone_wall");
        SANDSTONE_WALL = new WallBlock(Identifier.of(MOD_ID, "sandstone_wall"), Material.STONE).setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.PISTON_SOUNDS).setTranslationKey(MOD_ID, "sandstone_wall");
        BRICK_WALL = new WallBlock(Identifier.of(MOD_ID, "brick_wall"), Material.STONE).setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.PISTON_SOUNDS).setTranslationKey(MOD_ID, "brick_wall");
    }
}
