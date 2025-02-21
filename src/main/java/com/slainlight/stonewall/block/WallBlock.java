package com.slainlight.stonewall.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class WallBlock extends TemplateBlock
{
    private static final BooleanProperty UP;
    public static final EnumProperty<WallShape> NORTH_SHAPE;
    public static final EnumProperty<WallShape> SOUTH_SHAPE;
    public static final EnumProperty<WallShape> WEST_SHAPE;
    public static final EnumProperty<WallShape> EAST_SHAPE;
    private static World world;

    static
    {
        UP = Properties.UP;
        NORTH_SHAPE = WallProperties.NORTH_WALL_SHAPE;
        SOUTH_SHAPE = WallProperties.SOUTH_WALL_SHAPE;
        WEST_SHAPE = WallProperties.WEST_WALL_SHAPE;
        EAST_SHAPE = WallProperties.EAST_WALL_SHAPE;
    }

    public WallBlock(Identifier identifier, Material material)
    {
        super(identifier, material);
        setDefaultState(getStateManager().getDefaultState().with(UP, true).with(NORTH_SHAPE, WallShape.NONE).with(EAST_SHAPE, WallShape.NONE).with(SOUTH_SHAPE, WallShape.NONE).with(WEST_SHAPE, WallShape.NONE));
        setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE);
    }

    private boolean canConnect(BlockState block)
    {
        return block.getBlock().isFullCube() || block.getBlock() instanceof WallBlock;
    }

    public BlockState determineBlockState(World world, int x, int y, int z)
    {
        boolean up = true;
        boolean north = canConnect(world.getBlockState(x, y, z - 1));
        boolean south = canConnect(world.getBlockState(x, y, z + 1));
        boolean west = canConnect(world.getBlockState(x - 1, y, z));
        boolean east = canConnect(world.getBlockState(x + 1, y, z));

        boolean nsWall = north && south && !(west || east);
        boolean weWall = west && east && !(north || south);

        boolean canUpBeFalse = nsWall || weWall;

        boolean tallNorth = false;
        boolean tallSouth = false;
        boolean tallWest = false;
        boolean tallEast = false;

        if (world.getBlockState(x, y + 1, z).getBlock() instanceof WallBlock)
        {
            if (canUpBeFalse)
            {
                BlockState above = determineBlockState(world, x, y + 1, z);
                up = above.get(Properties.UP);
            }

            tallNorth = canConnect(world.getBlockState(x, y + 1, z - 1));
            tallSouth = canConnect(world.getBlockState(x, y + 1, z + 1));
            tallWest = canConnect(world.getBlockState(x - 1, y + 1, z));
            tallEast = canConnect(world.getBlockState(x + 1, y + 1, z));
        } else
        {
            up = !canUpBeFalse;
        }

        if (world.getBlockState(x, y + 1, z).getBlock().isFullCube())
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
    public void neighborUpdate(World world, int x, int y, int z, int l)
    {
        world.setBlockState(x, y, z, determineBlockState(world, x, y, z));

        if (world.getBlockState(x, y - 1, z).getBlock() instanceof WallBlock wallBlock)
        {
            wallBlock.neighborUpdate(world, x, y - 1, z, 0);
        }

        super.neighborUpdate(world, x, y, z, l);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity living)
    {
        if (world.getBlockState(x, y, z).getBlock() instanceof WallBlock)
            world.setBlockState(x, y, z, determineBlockState(world, x, y, z));
    }

    protected Box getShape(BlockState block, boolean collider, boolean bounding)
    {
        if (!(block.getBlock() instanceof WallBlock))
            return Box.create(0.F, 0.F, 0.F, 1.F, 1.F, 1.F);
        if (block.get(Properties.UP))
        {
            if (bounding)
            {
                boolean north = !block.get(NORTH_SHAPE).equals(WallShape.NONE);
                boolean south = !block.get(SOUTH_SHAPE).equals(WallShape.NONE);
                boolean west = !block.get(WEST_SHAPE).equals(WallShape.NONE);
                boolean east = !block.get(EAST_SHAPE).equals(WallShape.NONE);
                return Box.create(west ? 0.F : 0.25, 0.F, north ? 0.F : 0.25F, east ? 1.F : 0.75F, collider ? 1.5F : 1.F, south ? 1.F : 0.75F);
            }
            return Box.create(0.25, 0.F, 0.25F, 0.75F, collider ? 1.5F : 1.F, 0.75F);
        } else
        {
            if (!block.get(EAST_SHAPE).equals(WallShape.NONE) && !block.get(WEST_SHAPE).equals(WallShape.NONE))
            {
                boolean tall = block.get(EAST_SHAPE).equals(WallShape.TALL) && block.get(WEST_SHAPE).equals(WallShape.TALL);
                return Box.create(0.F, 0.F, 0.3125F, 1.F, collider ? 1.5F : (tall ? 1.F : 0.875F), 0.6875);
            } else
            {
                boolean tall = block.get(NORTH_SHAPE).equals(WallShape.TALL) && block.get(SOUTH_SHAPE).equals(WallShape.TALL);
                return Box.create(0.3125, 0.F, 0.F, 0.6875, collider ? 1.5F : (tall ? 1.F : 0.875F), 1.F);
            }
        }
    }

    protected Box addBasePos(Box box, int x, int y, int z)
    {
        box.minX += x;
        box.minY += y;
        box.minZ += z;
        box.maxX += x;
        box.maxY += y;
        box.maxZ += z;

        return box;
    }

    @Override
    public Box getCollisionShape(World world, int i, int j, int k)
    {
        return addBasePos(getShape(world.getBlockState(i, j, k), true, false), i, j, k);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Box getBoundingBox(World arg, int i, int j, int k)
    {
        WallBlock.world = arg;
        return addBasePos(getShape(arg.getBlockState(i, j, k), false, false), i, j, k);
    }

    @Override
    public void updateBoundingBox(BlockView arg, int i, int j, int k)
    {
        if (world != null)
        {
            Box box = getShape(world.getBlockState(i, j, k), false, true);
            setBoundingBox((float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
        }
    }

    @Override
    public boolean canPlaceAt(World world, int i, int j, int k)
    {
        // method_175 is get entities by class
        List list = world.collectEntitiesByClass(Entity.class, Box.create(i, j, k, i + 1.F, j + 1.5F, k + 1.F));
        return list.size() == 0;
    }

    @Override
    public boolean isOpaque()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }
}
