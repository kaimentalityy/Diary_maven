package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;
import server.utils.exception.badrequest.ConstraintViolationExceptionCustom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LessonRespDto createLesson(@Valid @RequestBody LessonRqDto lessonRqDto) throws ConstraintViolationExceptionCustom {
        return facade.assignLesson(lessonRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable UUID id) throws ConstraintViolationExceptionCustom {
        facade.removeLesson(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/{localDateTime}")
    public List<Lesson> findAllLessonsByDate(@PathVariable LocalDateTime localDateTime) throws ConstraintViolationExceptionCustom {
        return facade.findAllLessonsByDate(localDateTime);
    }
}
