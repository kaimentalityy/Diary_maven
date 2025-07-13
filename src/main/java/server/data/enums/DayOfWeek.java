package server.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek {

    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private final int id;

    public static DayOfWeek getById(Integer id) {
        if (id == null) return null;
        for (DayOfWeek day : values()) {
            if (day.id == id) return day;
        }
        return null;
    }

}

