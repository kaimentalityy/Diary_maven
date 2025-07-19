package server.presentation.dto.response;

import java.util.UUID;

public record TeacherRespDto(UUID id, UUID subjectId, UUID teacherId) {
}
