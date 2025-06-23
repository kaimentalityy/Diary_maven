package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.request.UpdateGradeRqDto;
import server.presentation.dto.response.GradeRespDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradesController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GradeRespDto giveGrade(@Valid @RequestBody GradeRqDto gradeRqDto) {
        return facade.giveGrade(gradeRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void removeGrade(@PathVariable UUID id) {
        facade.removeGrade(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public GradeRespDto updateGrade(@Valid @RequestBody UpdateGradeRqDto updateGradeRqDto) {
        return facade.updateGrade(updateGradeRqDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pupil-grades")
    public List<String> findAllGradesOfPupil(@RequestParam UUID userId, @RequestParam UUID subjectId) {
        return facade.findAllGradesOfPupil(userId, subjectId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/average-grade")
    public Double calculateAverageGrade(@RequestParam UUID userId, @RequestParam UUID subjectId) {
        return facade.calculateAverageGrade(userId, subjectId);
    }
}

