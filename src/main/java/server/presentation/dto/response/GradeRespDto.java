package server.presentation.dto.response;

import java.util.UUID;

public record GradeRespDto(UUID id , UUID pupilId, UUID lessonId, String grade) {
}
