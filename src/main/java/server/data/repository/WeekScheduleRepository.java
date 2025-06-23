package server.data.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.WeekSchedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeekScheduleRepository extends JpaRepository<WeekSchedule, UUID> {

    @Query("SELECT w.lessonNumber FROM WeekSchedule w WHERE w.id = :id")
    Optional<Integer> findLessonNumberById(@Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM WeekSchedule w WHERE w.id <> :id AND w.lessonNumber = :lessonNumber AND w.weekDayId = :weekDayId AND w.lessonId = :lessonId")
    boolean doesWeekScheduleExist(@Param("id") UUID id,
                                  @Param("lessonNumber") Integer lessonNumber,
                                  @Param("weekDayId") Integer weekDayId,
                                  @Param("lessonId") UUID lessonId);

    @Query("SELECT COUNT(w) FROM WeekSchedule w WHERE w.lessonId = :lessonId")
    long countTotalLessonsForWeek(@Param("lessonId") UUID lessonId);

    List<WeekSchedule> findByWeekDayIdAndLessonId(Integer weekDayId, UUID lessonId);
}
