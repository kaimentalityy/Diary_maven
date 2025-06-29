package server.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.DayOfWeekRepository;
import server.data.repository.LessonRepository;
import server.data.repository.WeekScheduleRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.DayOfWeekCustomNotFoundException;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WeekScheduleService {

    private final WeekScheduleRepository weekScheduleRepository;
    private final LessonRepository lessonRepository;
    private final DayOfWeekRepository dayOfWeekRepository;

    public WeekSchedule insertLessonInSchedule(WeekSchedule schedule) {
        if (weekScheduleRepository.existsByLessonAndDayOfWeekAndLessonNumber(schedule.getLesson(), schedule.getDayOfWeek(), schedule.getLessonNumber())) {
            throw new DatabaseOperationExceptionCustom("Schedule already exists.");
        }
        return weekScheduleRepository.save(schedule);
    }

    public void unscheduleLessonFromSchedule(UUID id) {
        if (!weekScheduleRepository.existsById(id)) {
            throw new WeekScheduleCustomNotFoundException("Lesson not found");
        }
        weekScheduleRepository.deleteById(id);
    }

    public Integer getLessonPlace(UUID id) {
        return weekScheduleRepository.findLessonNumberById(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found"));
    }

    public Double countTotalHoursAWeek(UUID lessonId) {
        return (double) weekScheduleRepository.countTotalLessonsForWeek(lessonId);
    }

    public List<Lesson> getAllLessonsInADay(int dayOfWeekValue, UUID lessonId) {
        DayOfWeek dayOfWeek = dayOfWeekRepository.findById(dayOfWeekValue)
                .orElseThrow(() -> new DayOfWeekCustomNotFoundException(dayOfWeekValue));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found: " + lessonId));

        List<WeekSchedule> entries = weekScheduleRepository.findByDayOfWeekAndLesson(dayOfWeek, lesson);

        return entries.stream()
                .map(WeekSchedule::getLesson)
                .toList();
    }
}
