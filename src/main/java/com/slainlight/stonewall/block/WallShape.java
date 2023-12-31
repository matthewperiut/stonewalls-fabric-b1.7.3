package com.slainlight.stonewall.block;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum WallShape implements StringIdentifiable
{
    NONE("none"),
    LOW("low"),
    TALL("tall");

    private final String name;

    WallShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.asString();
    }

    public String asString() {
        return this.name;
    }
}
