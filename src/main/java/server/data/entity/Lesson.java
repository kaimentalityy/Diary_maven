package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Lesson {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID classId;
    private UUID teacherOfSubjectId;
    private UUID subjectId;
    private LocalDateTime date;
}
