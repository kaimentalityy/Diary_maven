package server.presentation.dto.request;

import java.util.UUID;

public record CreateUserRqDto(String login, String password, String name, String lastname, UUID roleId, UUID classId) {
}
