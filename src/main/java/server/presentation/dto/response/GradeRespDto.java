package server.presentation.dto.response;

import java.util.UUID;

public record GradeRespDto(UUID id , UUID pupil_id, UUID lesson_id, String grade) {
}
