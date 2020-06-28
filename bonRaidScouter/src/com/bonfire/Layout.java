package com.bonfire;

import java.util.ArrayList;
import java.util.List;

public class Layout {
    private final List<Room> rooms = new ArrayList<>();

    public void add(Room room)
    {
        rooms.add(room);
    }

    public Room getRoomAt(int position)
    {
        for (Room room : rooms)
        {
            if (room.getPosition() == position)
            {
                return room;
            }
        }

        return null;
    }

    public String toCode()
    {
        StringBuilder builder = new StringBuilder();

        for (Room room : rooms)
        {
            builder.append(room.getSymbol());
        }

        return builder.toString();
    }

    public String toCodeString()
    {
        return toCode().replaceAll("#", "").replaceAll("¤", "");
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
