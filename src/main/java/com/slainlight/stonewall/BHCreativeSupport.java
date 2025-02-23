package com.slainlight.stonewall;

import com.slainlight.stonewall.block.BlockListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class BHCreativeSupport {
    public static CreativeTab tabStonewall;

    @EventListener
    public void onTabInit(TabRegistryEvent event){
        tabStonewall = new SimpleTab(BlockListener.MOD_ID.id("stone_wall"), BlockListener.STONE_WALL.asItem());
        event.register(tabStonewall);
        for (Block block : BlockListener.blocks){
            tabStonewall.addItem(new ItemStack(block.asItem(), 1));
        }
    }
}