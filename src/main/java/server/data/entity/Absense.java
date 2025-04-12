package server.data.entity;

import lombok.*;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Data
public class Absense {
    private UUID id;
    private UUID lesson_id;
    private UUID pupil_id;
    private boolean is_absent;
    private Date date;

    public boolean is_absent() {
        return is_absent;
    }

    public void setAbsence(boolean is_absent) {
        this.is_absent = is_absent;
    }
}
