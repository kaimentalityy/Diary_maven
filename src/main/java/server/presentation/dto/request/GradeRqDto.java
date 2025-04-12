package server.presentation.dto.request;

import java.util.UUID;

public record GradeRqDto(UUID pupil_id, UUID lesson_id, String grade){
}
