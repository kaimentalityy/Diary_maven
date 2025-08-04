package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.service.SubjectService;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/gay")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectRespDto addSubject(@RequestBody SubjectRqDto subjectRqDto) {
        return subjectService.addSubject(subjectRqDto);
    }
}
