package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class TeacherOfSubject {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID teacherId;
    private UUID subjectId;
}
