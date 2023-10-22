package com.slainlight.stonewall.block;

import net.modificationstation.stationapi.api.state.property.EnumProperty;

public class WallProperties
{
    public static final EnumProperty<WallShape> EAST_WALL_SHAPE;
    public static final EnumProperty<WallShape> NORTH_WALL_SHAPE;
    public static final EnumProperty<WallShape> SOUTH_WALL_SHAPE;
    public static final EnumProperty<WallShape> WEST_WALL_SHAPE;

    static {
        EAST_WALL_SHAPE = EnumProperty.of("east", WallShape.class);
        NORTH_WALL_SHAPE = EnumProperty.of("north", WallShape.class);
        SOUTH_WALL_SHAPE = EnumProperty.of("south", WallShape.class);
        WEST_WALL_SHAPE = EnumProperty.of("west", WallShape.class);
    }
}
