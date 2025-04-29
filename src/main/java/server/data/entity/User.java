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
    private UUID roleId;
    private boolean isBlocked;
    private UUID classId;

}
