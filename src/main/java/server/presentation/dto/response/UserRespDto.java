package server.presentation.dto.response;

import java.util.UUID;

public record UserRespDto(
        UUID id,
        String login,
        UUID roleId,
        boolean blocked) {
}
