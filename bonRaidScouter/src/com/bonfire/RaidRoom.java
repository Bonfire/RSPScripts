package com.bonfire;

public enum RaidRoom {
    START("Start", RoomType.START),
    END("End", RoomType.END),
    SCAVENGERS("Scavengers", RoomType.SCAVENGERS),
    FARMING("Farming", RoomType.FARMING),
    EMPTY("Empty", RoomType.EMPTY),

    TEKTON("Tekton", RoomType.COMBAT),
    MUTTADILES("Muttadiles", RoomType.COMBAT),
    GUARDIANS("Guardians", RoomType.COMBAT),
    VESPULA("Vespula", RoomType.COMBAT),
    SHAMANS("Shamans", RoomType.COMBAT),
    VASA("Vasa", RoomType.COMBAT),
    VANGUARDS("Vanguards", RoomType.COMBAT),
    MYSTICS("Mystics", RoomType.COMBAT),
    UNKNOWN_COMBAT("Unknown (combat)", RoomType.COMBAT),

    CRABS("Crabs", RoomType.PUZZLE),
    ICE_DEMON("Ice Demon", RoomType.PUZZLE),
    TIGHTROPE("Tightrope", RoomType.PUZZLE),
    THIEVING("Thieving", RoomType.PUZZLE),
    UNKNOWN_PUZZLE("Unknown (puzzle)", RoomType.PUZZLE);

    static final int ROOM_MAX_SIZE = 32;

    private final String name;
    private final RoomType type;

    RaidRoom(String name, RoomType type) {
        this.name = name;
        this.type = type;
    }

    public RoomType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
