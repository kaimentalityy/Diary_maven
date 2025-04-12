package server.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRespDto(UUID id , UUID class_id, UUID teacher_of_subject_id, LocalDateTime date, UUID subject_id) {
}
