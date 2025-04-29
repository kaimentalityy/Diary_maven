package server.data.entity;

import lombok.Getter;

import java.util.UUID;

public enum Role {
    ADMIN(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0187")),
    PUPIL(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0183")),
    TEACHER(UUID.fromString("d65e91f2-68bd-4578-93cf-e0bc3ddd0185"));

    private final UUID uuid;

    Role(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Role fromRole(UUID uuid) {
        for (Role role : Role.values()) {
            if (role.getUuid().equals(uuid)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UUID: " + uuid);
    }
}

