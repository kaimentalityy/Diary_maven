package server.presentation.dto.request;

import java.sql.Date;
import java.util.UUID;

public record AbsenseRqDto(UUID lesson_id, UUID pupil_id, Boolean is_absent, Date date) {
}
