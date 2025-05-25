package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;
import server.utils.exception.badrequest.ConstraintViolationExceptionCustom;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TeacherRespDto addTeacher(@Valid @RequestBody TeacherRqDto teacherRqDto) {
        return facade.addTeacher(teacherRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable UUID id) throws ConstraintViolationExceptionCustom {
        facade.deleteTeacher(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public TeacherRespDto updateTeacher(@Valid @RequestBody UpdateTeacherRqDto updateTeacherRqDto) {
        return facade.updateTeacher(updateTeacherRqDto);
    }
}