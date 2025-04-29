package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.SubjectRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<SubjectRespDto> addSubject(@RequestBody SubjectRqDto subjectRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(subjectRqDto);
        return facade.createSubject(subjectRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteSubject(@PathVariable UUID id) throws SQLException, ConstraintViolationException {

        ResponseDto<Subject> subject = facade.findSubjectById(id);

        if (findSubjectById(subject.getResult().orElse(null).getId()).getResult().isPresent()) {
            Validator.notNull(subject);
            return facade.deleteSubject(subject.getResult().orElse(null));
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Subject not found"));
    }

    @GetMapping("/{id}")
    public ResponseDto<Subject> findSubjectById(@PathVariable UUID id) throws SQLException {
        return facade.findSubjectById(id);
    }

    @GetMapping
    public List<Subject> findAllSubjects() throws SQLException {
        return facade.findAllSubjects();
    }

    @GetMapping("/{name}")
    public ResponseDto<Subject> findSubjectByName(@PathVariable String name) throws SQLException {
        return facade.findSubjectByName(name);
    }
}
