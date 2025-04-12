package server.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRqDto(UUID class_id, UUID teacher_of_subject_id, LocalDateTime date, UUID subject_id) {
}
