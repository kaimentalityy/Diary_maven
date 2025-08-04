package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.enums.DayOfWeek;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeekScheduleRepository extends JpaRepository<WeekSchedule, UUID> {

    @Query("SELECT w.lessonNumber FROM WeekSchedule w WHERE w.id = :id")
    Optional<Integer> findLessonNumberById(@Param("id") UUID id);

    @Query("SELECT COUNT(w) FROM WeekSchedule w WHERE w.lesson = :lessonId")
    long countTotalLessonsForWeek(@Param("lessonId") UUID lessonId);

    List<WeekSchedule> findByDayOfWeekAndLesson(DayOfWeek dayOfWeek, Lesson lesson);

    boolean existsByLessonAndDayOfWeekAndLessonNumber(Lesson lesson, DayOfWeek dayOfWeek, Integer lessonNumber);
}
