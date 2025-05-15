package server.data.entity;

import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Data
public class Absense {
    private UUID id;
    private UUID lessonId;
    private UUID pupilId;
    private Boolean isPresent;
    private Date date;

}
