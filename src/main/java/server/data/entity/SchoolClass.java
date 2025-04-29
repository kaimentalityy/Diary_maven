package server.data.entity;

import lombok.*;

import java.util.UUID;

@Data
public class SchoolClass {

    private UUID id;
    private String letter;
    private String number;
    private UUID teacherId;

}