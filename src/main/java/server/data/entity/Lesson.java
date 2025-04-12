package server.data.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Lesson {
    private UUID id;
    private UUID class_id;
    private UUID teacher_of_subject_id;
    private UUID subject_id;
    private LocalDateTime date;
}
