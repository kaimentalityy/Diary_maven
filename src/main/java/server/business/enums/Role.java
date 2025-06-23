package server.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0187")),
    PUPIL(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0183")),
    TEACHER(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0185"));

    private final UUID uuid;
}

