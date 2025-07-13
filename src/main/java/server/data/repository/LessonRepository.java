package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.data.entity.Lesson;
import server.data.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findAllByLessonDate(LocalDateTime lessonDate);

    List<Lesson> findBySubject(Subject subject);
}
