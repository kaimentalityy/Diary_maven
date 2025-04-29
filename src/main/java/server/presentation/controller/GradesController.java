package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Grades;
import server.data.entity.User;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.GradeRespDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradesController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<GradeRespDto> giveGrade(@RequestBody GradeRqDto gradeRqDto) throws SQLException, ConstraintViolationException {
        Validator.notNull(gradeRqDto);
        return facade.giveGrade(gradeRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> removeGrade(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        if (findGradeById(id).getResult().isPresent()) {
            Validator.notNull(id);
            return facade.removeGrade(findGradeById(id).getResult().get());
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Grades not found"));
    }

    @PatchMapping("/{id}")
    public ResponseDto<Void> updateGrade(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        if (findGradeById(id).getResult().isPresent()) {
            Validator.notNull(id);
            return facade.updateGrade(id);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Grades not found"));
    }

    @GetMapping("/{id}")
    public ResponseDto<Grades> findGradeById(@PathVariable UUID id) throws SQLException {
        return facade.findGradeById(id);
    }

    @GetMapping("/pupil-grades")
    public List<String> findAllGradesOfPupil(@RequestParam UUID userId, @RequestParam UUID subjectId) throws SQLException {
        User user = new User();
        user.setId(userId);
        return facade.findAllGradesOfPupil(user, subjectId);
    }

    @GetMapping("/average-grade")
    public ResponseDto<Double> calculateAverageGrade(@RequestParam UUID userId, @RequestParam UUID subjectId) throws SQLException {
        User user = new User();
        user.setId(userId);
        return facade.calculateAverageGrade(user, subjectId);
    }
}

