package com.rsscripting.serveradmin.gui;

import java.util.ArrayList;
import java.util.List;

public class MenuLayoutUtils {

    public static List<Integer> getCenteredSlots(
            int itemCount
    ) {

        List<Integer> rowSlots =
                new ArrayList<>();

        if (itemCount <= 0) {
            return rowSlots;
        }

        if (itemCount >= 9) {

            for (int i = 0; i < 9; i++) {
                rowSlots.add(i);
            }

            return rowSlots;

        }

        if (itemCount % 2 == 0) {

            switch (itemCount) {

                case 2 -> rowSlots = List.of(
                        3, 5
                );

                case 4 -> rowSlots = List.of(
                        2, 3, 5, 6
                );

                case 6 -> rowSlots = List.of(
                        1, 2, 3, 5, 6, 7
                );

                case 8 -> rowSlots = List.of(
                        0, 1, 2, 3, 5, 6, 7, 8
                );

            }

        } else {

            int startSlot =
                    (9 - itemCount) / 2;

            for (int i = 0; i < itemCount; i++) {

                rowSlots.add(
                        startSlot + i
                );

            }

        }

        return rowSlots;

    }

}