package server.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject {
    CHEMISTRY(0),
    MATH(1),
    PHYSICS(2),
    ENGLISH(3),
    BIOLOGY(4),
    GEOGRAPHY(5),
    HISTORY(6),
    PHILOSOPHY(7);

    private final int id;

    public static String getValueById(Integer id) {
        if (id == null) {
            return null;
        }

        for (Subject subject : values()) {
            if (subject.id == id) {
                return subject.name();
            }
        }
        throw new IllegalArgumentException("No subject found with id: " + id);
    }

    public static Subject getById(Integer id) {
        if (id == null) return null;
        for (Subject subject : values()) {
            if (subject.id == id) return subject;
        }
        return null;
    }
}