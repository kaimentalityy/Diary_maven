package server.presentation.dto.request;

import java.util.UUID;

public record SchoolClassRqDto(String letter, String number, UUID teacher_id) {
}
