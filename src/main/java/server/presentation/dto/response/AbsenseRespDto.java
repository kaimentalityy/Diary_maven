package server.presentation.dto.response;

import java.sql.Date;
import java.util.UUID;

public record AbsenseRespDto(UUID id, UUID lessonId, UUID pupilId, Boolean present, Date date) {
}
