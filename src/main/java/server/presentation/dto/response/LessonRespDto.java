package server.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRespDto(UUID id , UUID classId, UUID teacherOfSubjectId, LocalDateTime lessonDate, Integer subjectId) {
}
