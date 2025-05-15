package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;
import server.utils.Validator;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<SubjectRespDto> addSubject(@RequestBody SubjectRqDto subjectRqDto) {
        Validator.notNull(subjectRqDto);
        SubjectRespDto subjectRespDto = facade.createSubject(subjectRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectRespDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable UUID id) {
        Validator.notNull(id);
        facade.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Subject> findSubjectById(@PathVariable UUID id) {
        Validator.notNull(id);
        Subject subject = facade.findSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping
    public ResponseEntity<List<Subject>> findAllSubjects() {
        List<Subject> subjects = facade.findAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Subject> findSubjectByName(@PathVariable String name) {
        Validator.notNull(name);
        Subject subject = facade.findSubjectByName(name);
        return ResponseEntity.ok(subject);
    }
}
