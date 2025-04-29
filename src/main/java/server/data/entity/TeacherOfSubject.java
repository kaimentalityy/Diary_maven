package server.data.entity;

import lombok.Data;

import java.util.UUID;

@Data

public class TeacherOfSubject {
    private UUID id;
    private UUID teacherId;
    private UUID subjectId;
}
