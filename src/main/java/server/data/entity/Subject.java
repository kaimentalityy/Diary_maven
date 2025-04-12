package server.data.entity;

import lombok.Data;

import java.util.UUID;

@Data

public class Subject {
    private UUID id;
    private String name;
}
