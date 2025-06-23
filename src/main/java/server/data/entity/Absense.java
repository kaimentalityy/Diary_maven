package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Data
@Entity
public class Absense {
    @Id
    private UUID id =  UUID.randomUUID();
    private UUID lessonId;
    private UUID pupilId;
    private Boolean isPresent;
    private Date date;
}
