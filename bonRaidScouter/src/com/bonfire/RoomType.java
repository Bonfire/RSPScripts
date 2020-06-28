package com.bonfire;

public enum RoomType {
    START("Start", '#'),
    END("End", '¤'),
    SCAVENGERS("Scavengers", 'S'),
    FARMING("Farming", 'F'),
    EMPTY("Empty", ' '),
    COMBAT("Combat", 'C'),
    PUZZLE("Puzzle", 'P');

    private final String name;
    private final char code;

    RoomType(String name, char code) {
        this.name = name;
        this.code = code;
    }

    RaidRoom getUnsolvedRoom()
    {
        switch (this)
        {
            case START:
                return RaidRoom.START;
            case END:
                return RaidRoom.END;
            case SCAVENGERS:
                return RaidRoom.SCAVENGERS;
            case FARMING:
                return RaidRoom.FARMING;
            case COMBAT:
                return RaidRoom.UNKNOWN_COMBAT;
            case PUZZLE:
                return RaidRoom.UNKNOWN_PUZZLE;
            case EMPTY:
            default:
                return RaidRoom.EMPTY;
        }
    }

    static RoomType fromCode(char code)
    {
        for (RoomType type : values())
        {
            if (type.getCode() == code)
            {
                return type;
            }
        }

        return EMPTY;
    }

    char getCode() {
        return code;
    }
}
