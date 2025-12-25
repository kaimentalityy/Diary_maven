package server.presentation.dto.response;

import java.util.UUID;

public record AbsenseRespDto(
        UUID id,
        UUID lessonId,
        UUID pupilId,
        Boolean present) {
}
