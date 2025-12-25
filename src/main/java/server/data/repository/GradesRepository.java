package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.data.entity.Grade;
import server.data.entity.Lesson;
import server.data.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradesRepository extends JpaRepository<Grade, UUID> {

    List<Grade> findByPupilAndLesson_IdIn(User pupil, List<UUID> lessonIds);

    boolean existsByPupilAndLessonAndGradeAndIdNot(User pupil, Lesson lesson, String grade, UUID id);
}
