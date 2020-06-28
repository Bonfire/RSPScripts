package com.bonfire;

import java.util.ArrayList;
import java.util.List;

public class Raid {
    private final RaidRoom[] rooms = new RaidRoom[16];
    private Layout layout;

    void updateLayout(Layout layout)
    {
        if (layout == null)
        {
            return;
        }

        this.layout = layout;

        for (int i = 0; i < rooms.length; i++)
        {
            if (layout.getRoomAt(i) == null)
            {
                continue;
            }

            RaidRoom room = rooms[i];

            if (room == null)
            {
                RoomType type = RoomType.fromCode(layout.getRoomAt(i).getSymbol());
                room = type.getUnsolvedRoom();
                setRoom(room, i);
            }
        }
    }

    public RaidRoom getRoom(int position)
    {
        return rooms[position];
    }

    public void setRoom(RaidRoom room, int position)
    {
        if (position < rooms.length)
        {
            rooms[position] = room;
        }
    }

    RaidRoom[] getCombatRooms()
    {
        List<RaidRoom> combatRooms = new ArrayList<>();

        for (Room room : layout.getRooms())
        {
            if (room == null)
            {
                continue;
            }

            if (rooms[room.getPosition()].getType() == RoomType.COMBAT)
            {
                combatRooms.add(rooms[room.getPosition()]);
            }
        }

        return combatRooms.toArray(new RaidRoom[0]);
    }

    void setCombatRooms(RaidRoom[] combatRooms)
    {
        int index = 0;

        for (Room room : layout.getRooms())
        {
            if (room == null)
            {
                continue;
            }

            if (rooms[room.getPosition()].getType() == RoomType.COMBAT)
            {
                rooms[room.getPosition()] = combatRooms[index];
                index++;
            }
        }
    }

    public String toCode()
    {
        StringBuilder builder = new StringBuilder();

        for (RaidRoom room : rooms)
        {
            if (room != null)
            {
                builder.append(room.getType().getCode());
            }
            else
            {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    /**
     * Get the raid rooms in the order they are in the raid
     * @return
     */
    List<RaidRoom> getOrderedRooms()
    {
        List<RaidRoom> orderedRooms = new ArrayList<>();
        for (Room r : getLayout().getRooms())
        {
            final int position = r.getPosition();
            final RaidRoom room = getRoom(position);

            if (room == null)
            {
                continue;
            }

            orderedRooms.add(room);
        }

        return orderedRooms;
    }

    Layout getLayout() {
        return layout;
    }

    String toRoomString()
    {
        final StringBuilder sb = new StringBuilder();

        for (RaidRoom room : getOrderedRooms())
        {
            switch (room.getType())
            {
                case PUZZLE:
                case COMBAT:
                    sb.append(room.getName()).append(", ");
                    break;
            }
        }

        final String roomsString = sb.toString();
        return roomsString.substring(0, roomsString.length() - 2);
    }
}
