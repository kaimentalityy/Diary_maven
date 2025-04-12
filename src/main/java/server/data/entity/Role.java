package server.data.entity;

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

    public static Role fromRole(UUID uuid) {
        for (Role role : Role.values()) {
            if (role.getUuid().equals(uuid)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UUID: " + uuid);
    }
}
