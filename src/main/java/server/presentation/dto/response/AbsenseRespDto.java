package server.presentation.dto.response;

import java.sql.Date;
import java.util.UUID;

public record AbsenseRespDto(UUID id, UUID lesson_id, UUID pupil_id, Boolean is_absent, Date date) {
}
