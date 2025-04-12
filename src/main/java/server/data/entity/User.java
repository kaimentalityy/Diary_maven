package server.data.entity;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String name;
    private String lastname;
    private String login;
    private String password;
    private UUID role_id;
    private boolean isBlocked;
    private UUID class_id;
}
