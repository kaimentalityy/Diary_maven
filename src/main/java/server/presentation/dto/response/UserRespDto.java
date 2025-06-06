package server.presentation.dto.response;

import java.util.UUID;

public record UserRespDto(UUID id, String login, String name, String lastname, UUID role, boolean isBlocked, UUID classId, Integer age) {
}
