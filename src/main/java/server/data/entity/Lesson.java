package server.data.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Lesson {
    private UUID id;
    private UUID classId;
    private UUID teacherOfSubjectId;
    private UUID subjectId;
    private LocalDateTime date;
}
