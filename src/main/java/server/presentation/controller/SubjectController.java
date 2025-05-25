package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SubjectRespDto addSubject(@Valid @RequestBody SubjectRqDto subjectRqDto) {
        return facade.createSubject(subjectRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable UUID id) {
        facade.deleteSubject(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Subject> findAllSubjects() {
        return facade.findAllSubjects();
    }
}
