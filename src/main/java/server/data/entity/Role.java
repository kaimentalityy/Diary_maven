package server.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

}

