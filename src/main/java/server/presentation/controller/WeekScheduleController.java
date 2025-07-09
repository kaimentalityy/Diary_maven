package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.business.service.WeekScheduleService;
import server.data.entity.Lesson;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/week-schedules")
public class WeekScheduleController {

    private final MainFacade facade;
    private final WeekScheduleService weekScheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public WeekScheduleRespDto addWeekSchedule(@Valid @RequestBody WeekScheduleRqDto weekScheduleRqDto) {
        return weekScheduleService.addLessonWeekSchedule(weekScheduleRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void removeLessonFromSchedule(@PathVariable UUID id) {
        facade.removeLessonFromSchedule(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/day-lessons")
    public List<Lesson> findAllLessonsInADay(@RequestParam int dayOfWeekId, @RequestParam UUID schoolClassId) {
        return facade.findAllLessonsInADay(facade.findDayOfWeekById(dayOfWeekId), schoolClassId);
    }
}

