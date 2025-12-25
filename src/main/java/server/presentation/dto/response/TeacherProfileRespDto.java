package server.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record TeacherProfileRespDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDate hireDate) {
}
