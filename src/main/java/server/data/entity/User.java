package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String lastname;
    private String login;
    private String password;
    private UUID roleId;
    private boolean isBlocked;
    private UUID classId;
    private Integer age;

}
