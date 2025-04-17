package server.data.entity;

import lombok.Data;

import java.util.UUID;

@Data

public class Grades {
    private UUID id;
    private UUID pupilId;
    private String grade;
    private UUID lessonId;
}
