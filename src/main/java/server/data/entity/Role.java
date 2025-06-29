package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    private UUID id;

    private String name;

}

