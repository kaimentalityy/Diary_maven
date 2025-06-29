package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dayofweek")
public class DayOfWeek {

    @Id
    private int id;

    private String name;

}

