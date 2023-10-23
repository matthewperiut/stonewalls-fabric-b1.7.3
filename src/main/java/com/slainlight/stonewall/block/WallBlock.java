package com.slainlight.stonewall.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class WallBlock extends TemplateBlockBase
{
    private static Level level;
    private static final BooleanProperty UP;
    public static final EnumProperty<WallShape> EAST_SHAPE;
    public static final EnumProperty<WallShape> NORTH_SHAPE;
    public static final EnumProperty<WallShape> SOUTH_SHAPE;
    public static final EnumProperty<WallShape> WEST_SHAPE;

    public WallBlock(Identifier identifier, Material material)
    {
        super(identifier, material);
        setDefaultState(getStateManager().getDefaultState().with(UP, true).with(NORTH_SHAPE, WallShape.NONE).with(EAST_SHAPE, WallShape.NONE).with(SOUTH_SHAPE, WallShape.NONE).with(WEST_SHAPE, WallShape.NONE));
        setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
    }

    @Override
    public boolean isFullOpaque()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    private boolean canConnect(BlockState block)
    {
        return block.getBlock().isFullCube() || block.getBlock() instanceof WallBlock;
    }

    public BlockState determineBlockState(Level level, int x, int y, int z)
    {
        boolean up = true;
        boolean north = canConnect(level.getBlockState(x-1,y,z));
        boolean south = canConnect(level.getBlockState(x+1,y,z));
        boolean west = canConnect(level.getBlockState(x,y,z+1));
        boolean east = canConnect(level.getBlockState(x,y,z-1));

        boolean nsWall = north && south && !(west || east);
        boolean weWall = west && east && !(north || south);

        boolean canUpBeFalse = nsWall || weWall;

        boolean tallNorth = false;
        boolean tallSouth = false;
        boolean tallWest = false;
        boolean tallEast = false;

        if (canUpBeFalse)
        {
            if (level.getBlockState(x,y+1,z).getBlock() instanceof WallBlock)
            {
                BlockState above = determineBlockState(level, x, y + 1, z);
                up = above.get(Properties.UP);
            }
            else
            {
                up = false;
            }
        }

        if (level.getBlockState(x,y+1,z).getBlock() instanceof WallBlock)
        {
            tallNorth = canConnect(level.getBlockState(x-1,y+1,z));
            tallSouth = canConnect(level.getBlockState(x+1,y+1,z));
            tallWest = canConnect(level.getBlockState(x,y+1,z+1));
            tallEast = canConnect(level.getBlockState(x,y+1,z-1));
        }

        if (level.getBlockState(x,y+1,z).getBlock().isFullCube())
        {
            tallNorth = true;
            tallSouth = true;
            tallWest = true;
            tallEast = true;
        }

        return getDefaultState().with(UP, up)
                .with(NORTH_SHAPE, north ? (tallNorth ? WallShape.TALL : WallShape.LOW) : WallShape.NONE)
                .with(SOUTH_SHAPE, south ? (tallSouth ? WallShape.TALL : WallShape.LOW) : WallShape.NONE)
                .with(WEST_SHAPE, west ? (tallWest ? WallShape.TALL : WallShape.LOW) : WallShape.NONE)
                .with(EAST_SHAPE, east ? (tallEast ? WallShape.TALL : WallShape.LOW) : WallShape.NONE);
    }

    @Override
    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int l)
    {
        WallBlock.level = level;
        level.setBlockState(x,y,z,determineBlockState(level,x,y,z));

        if (level.getBlockState(x,y-1,z).getBlock() instanceof WallBlock wallBlock)
        {
            wallBlock.onAdjacentBlockUpdate(level, x, y-1, z, 0);
        }

        super.onAdjacentBlockUpdate(level, x, y, z, l);
    }

    @Override
    public void afterPlaced(Level level, int x, int y, int z, Living living) {
        WallBlock.level = level;
        level.setBlockState(x,y,z,determineBlockState(level,x,y,z));
    }

    @Override
    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
        builder.add(UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE);
    }

    @Override
    public Box getCollisionShape(Level arg, int i, int j, int k) {
        return Box.create((double)i+0.25, j, (double)k+0.25, (double)i + 0.75, (double)j + 1.5F, (double)k + 0.75);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Box getOutlineShape(Level arg, int i, int j, int k) {

        BlockState block = arg.getBlockState(i,j,k);
        if (block.get(UP))
            return Box.create((double)i+0.25, j, (double)k+0.25, (double)i + 0.75, (double)j + 1.F, (double)k + 0.75);
        else
        {
            if (!block.get(EAST_SHAPE).equals(WallShape.NONE) && !block.get(WEST_SHAPE).equals(WallShape.NONE))
            {
                return Box.create((double)i+0.3125, j, k, (double)i + 0.6875, (double)j + 0.875F, (double)k + 1.F);
            }
            else
            {
                return Box.create(i, j, (double)k+0.3125, (double)i + 1.F, (double)j + 0.875F, (double)k + 0.6875);
            }
        }
    }

    @Override
    public void updateBoundingBox(BlockView arg, int i, int j, int k)
    {
        setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
        if (level != null)
        {
            BlockState block = level.getBlockState(i,j,k);
            if (!block.get(EAST_SHAPE).equals(WallShape.NONE) && !block.get(WEST_SHAPE).equals(WallShape.NONE))
            {
                setBoundingBox(0.3125F, 0.F, 0.F, 0.6875F, 0.875F, 1.F);
            }
            else
            {
                setBoundingBox(0.F, 0.F, 0.3125F, 1.F, 0.875F, 0.6875F);
            }
        }
    }

    static {
        UP = Properties.UP;
        EAST_SHAPE = WallProperties.EAST_WALL_SHAPE;
        NORTH_SHAPE = WallProperties.NORTH_WALL_SHAPE;
        SOUTH_SHAPE = WallProperties.SOUTH_WALL_SHAPE;
        WEST_SHAPE = WallProperties.WEST_WALL_SHAPE;
    }
}
