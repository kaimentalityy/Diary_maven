package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.WeekScheduleRepository;
import server.utils.exception.notfound.SchoolClassNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeekScheduleService {

    private final WeekScheduleRepository weekScheduleRepository;
    private final LessonService lessonService;

    public WeekSchedule insert(WeekSchedule weekSchedule) {
        weekSchedule.setId(UUID.randomUUID());
        return weekScheduleRepository.save(weekSchedule);
    }

    public void delete(WeekSchedule weekSchedule) {
        weekScheduleRepository.unscheduleLessonFromSchedule(weekSchedule);
    }

    public Integer getLessonPlace(UUID id) {
        return weekScheduleRepository.getLessonPlace(id);
    }

    public Double countTotalHours(UUID classId) {
        return weekScheduleRepository.countTotalHoursAWeek(lessonService.findByClassId(classId).orElseThrow(() -> new SchoolClassNotFoundException(classId)));
    }

    public Optional<WeekSchedule> findLessonById(UUID id) {
        return weekScheduleRepository.findLessonInSchedule(id);
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) {
        return weekScheduleRepository.getAllLessonsInADay(dayOfWeek, lessonService.findByClassId(classId).orElseThrow(() -> new SchoolClassNotFoundException(classId)));
    }

    public boolean doesWeekScheduleExist(UUID id) {
        return weekScheduleRepository.doesWeekScheduleExist(id);
    }
}
