package server.presentation.dto.request;

import java.util.UUID;

public record TeacherRqDto(UUID subjectId, UUID teacherId) {
}
