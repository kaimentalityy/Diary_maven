package server.business.service;

import org.springframework.stereotype.Service;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.WeekScheduleRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WeekScheduleService {

    private final WeekScheduleRepository weekScheduleRepository;
    private final LessonService lessonService;

    public WeekScheduleService() throws SQLException {
        weekScheduleRepository = new WeekScheduleRepository();
        lessonService = new LessonService();
    }

    public WeekSchedule insert(WeekSchedule weekSchedule) throws SQLException {
        weekSchedule.setId(UUID.randomUUID());
        return weekScheduleRepository.save(weekSchedule);
    }

    public void delete(WeekSchedule weekSchedule) throws SQLException {
        weekScheduleRepository.unscheduleLessonFromSchedule(weekSchedule);
    }

    public Integer getLessonPlace(UUID id) throws SQLException {
        return weekScheduleRepository.getLessonPlace(id);
    }

    public Double countTotalHours(UUID classId) throws SQLException {
        return weekScheduleRepository.countTotalHoursAWeek(lessonService.findByClassId(classId).orElse(null));
    }

    public Optional<WeekSchedule> findLessonById(UUID id) throws SQLException {
        return weekScheduleRepository.findLessonInSchedule(id);
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) throws SQLException {
        return weekScheduleRepository.getAllLessonsInADay(dayOfWeek, lessonService.findByClassId(classId).orElse(null));
    }
}
