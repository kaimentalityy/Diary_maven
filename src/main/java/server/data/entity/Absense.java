package server.data.entity;

import lombok.*;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Data
public class Absense {
    private UUID id;
    private UUID lessonId;
    private UUID pupilId;
    private Boolean isAbsent;
    private Date date;

    public boolean is_absent() {
        return isAbsent;
    }

    public void setAbsence(boolean is_absent) {
        this.isAbsent = is_absent;
    }

}
