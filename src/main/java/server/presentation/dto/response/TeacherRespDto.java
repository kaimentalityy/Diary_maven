package server.presentation.dto.response;

import java.util.UUID;

public record TeacherRespDto(UUID id, Integer subjectId, UUID teacherId) {
}
