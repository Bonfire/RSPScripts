package com.bonfire;

import java.util.Arrays;
import java.util.List;
import static java.lang.Math.floorMod;

import static com.bonfire.RaidRoom.*;

public class RotationSolver {
    private static final List[] ROTATIONS =
            {
                    Arrays.asList(TEKTON, VASA, GUARDIANS, MYSTICS, SHAMANS, MUTTADILES, VANGUARDS, VESPULA),
                    Arrays.asList(TEKTON, MUTTADILES, GUARDIANS, VESPULA, SHAMANS, VASA, VANGUARDS, MYSTICS),
                    Arrays.asList(VESPULA, VANGUARDS, MUTTADILES, SHAMANS, MYSTICS, GUARDIANS, VASA, TEKTON),
                    Arrays.asList(MYSTICS, VANGUARDS, VASA, SHAMANS, VESPULA, GUARDIANS, MUTTADILES, TEKTON)
            };

    static boolean solve(RaidRoom[] rooms)
    {
        if (rooms == null)
        {
            return false;
        }

        List<RaidRoom> match = null;
        Integer start = null;
        Integer index = null;
        int known = 0;

        for (int i = 0; i < rooms.length; i++)
        {
            if (rooms[i] == null || rooms[i].getType() != RoomType.COMBAT || rooms[i] == UNKNOWN_COMBAT)
            {
                continue;
            }

            if (start == null)
            {
                start = i;
            }

            known++;
        }

        if (known < 2)
        {
            return false;
        }

        if (known == rooms.length)
        {
            return true;
        }

        for (List rotation : ROTATIONS)
        {
            COMPARE:
            for (int i = 0; i < rotation.size(); i++)
            {
                if (rooms[start] == rotation.get(i))
                {
                    for (int j = start + 1; j < rooms.length; j++)
                    {
                        if (rooms[j].getType() != RoomType.COMBAT || rooms[j] == UNKNOWN_COMBAT)
                        {
                            continue;
                        }

                        if (rooms[j] != rotation.get(floorMod(i + j - start, rotation.size())))
                        {
                            break COMPARE;
                        }
                    }

                    if (match != null && match != rotation)
                    {
                        return false;
                    }

                    index = i - start;
                    match = rotation;
                }
            }
        }

        if (match == null)
        {
            return false;
        }

        for (int i = 0; i < rooms.length; i++)
        {
            if (rooms[i] == null)
            {
                continue;
            }

            if (rooms[i].getType() != RoomType.COMBAT || rooms[i] == UNKNOWN_COMBAT)
            {
                rooms[i] = match.get(floorMod(index + i, match.size()));
            }
        }

        return true;
    }
}
