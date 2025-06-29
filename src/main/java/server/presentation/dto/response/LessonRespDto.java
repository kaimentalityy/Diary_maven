package server.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRespDto(UUID id , UUID class_id, UUID teacherOfSubjectId, LocalDateTime date, UUID subject_id) {
}
