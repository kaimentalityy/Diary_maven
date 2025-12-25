package server.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record StudentProfileRespDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        LocalDate enrollmentDate,
        UUID classId) {
}
