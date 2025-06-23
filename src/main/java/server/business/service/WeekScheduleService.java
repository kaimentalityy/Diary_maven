package server.business.service;

import org.springframework.stereotype.Service;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.LessonRepository;
import server.data.repository.WeekScheduleRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class WeekScheduleService {

    private final WeekScheduleRepository repository;
    private final LessonRepository lessonRepository;

    public WeekScheduleService(WeekScheduleRepository repository, LessonRepository lessonRepository) {
        this.repository = repository;
        this.lessonRepository = lessonRepository;
    }

    public WeekSchedule insertLessonInSchedule(WeekSchedule schedule) {
        if (repository.existsById(schedule.getId())) {
            throw new DatabaseOperationExceptionCustom("Schedule already exists.");
        }
        return repository.save(schedule);
    }

    public void unscheduleLessonFromSchedule(UUID id) {
        if (!repository.existsById(id)) {
            throw new WeekScheduleCustomNotFoundException("Lesson not found");
        }
        repository.deleteById(id);
    }

    public Integer getLessonPlace(UUID id) {
        return repository.findLessonNumberById(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found"));
    }

    public Double countTotalHoursAWeek(UUID lessonId) {
        return (double) repository.countTotalLessonsForWeek(lessonId);
    }

    public List<Lesson> getAllLessonsInADay(int dayOfWeek, UUID lessonId) {
        List<WeekSchedule> entries = repository.findByWeekDayIdAndLessonId(dayOfWeek, lessonId);
        return entries.stream()
                .map(w -> lessonRepository.findById(w.getLessonId())
                        .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found: " + w.getLessonId())))
                .toList();
    }

    public boolean doesWeekScheduleExist(UUID id) {
        WeekSchedule w = repository.findById(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found"));
        return repository.doesWeekScheduleExist(id, w.getLessonNumber(), w.getWeekDayId(), w.getLessonId());
    }
}
