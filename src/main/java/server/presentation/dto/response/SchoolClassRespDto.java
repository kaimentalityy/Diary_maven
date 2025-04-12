package server.presentation.dto.response;

import java.util.UUID;

public record SchoolClassRespDto(UUID id, String letter, String number, UUID teacher_id) {
}
