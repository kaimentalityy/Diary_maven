package server.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final int value;

    public static Optional<DayOfWeek> getByValue(int value) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getValue() == value) {
                return Optional.of(day);
            }
        }
        return Optional.empty();
    }
}

