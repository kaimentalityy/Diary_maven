package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Grades;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.GradeRespDto;
import server.utils.Validator;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradesController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<GradeRespDto> giveGrade(@RequestBody GradeRqDto gradeRqDto) {
        Validator.notNull(gradeRqDto);
        GradeRespDto response = facade.giveGrade(gradeRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGrade(@PathVariable UUID id) {
        Validator.notNull(id);
        facade.removeGrade(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateGrade(@PathVariable UUID id) {
        Validator.notNull(id);
        facade.updateGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grades> findGradeById(@PathVariable UUID id) {
        Validator.notNull(id);
        Grades grade = facade.findGradeById(id);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/pupil-grades")
    public ResponseEntity<List<String>> findAllGradesOfPupil(@RequestParam UUID userId, @RequestParam UUID subjectId) {
        Validator.notNull(userId);
        Validator.notNull(subjectId);
        List<String> grades = facade.findAllGradesOfPupil(userId, subjectId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/average-grade")
    public ResponseEntity<Double> calculateAverageGrade(@RequestParam UUID userId, @RequestParam UUID subjectId) {
        Validator.notNull(userId);
        Validator.notNull(subjectId);
        Double averageGrade = facade.calculateAverageGrade(userId, subjectId);
        return ResponseEntity.ok(averageGrade);
    }
}

