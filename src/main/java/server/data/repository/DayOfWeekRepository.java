package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.data.entity.DayOfWeek;

public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Integer> {
}
