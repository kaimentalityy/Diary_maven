package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Data
@Entity
public class SchoolClass {
    @Id
    private UUID id = UUID.randomUUID();
    private String letter;
    private String number;
    private UUID teacherId;
    private Integer maxCapacity = 30;

}