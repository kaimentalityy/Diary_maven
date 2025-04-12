package server.presentation.controller;

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

public class SubjectController {

    private final MainFacade facade;

    public SubjectController() throws SQLException {
        facade = new MainFacade();
    }

    public ResponseDto<SubjectRespDto> addSubject(SubjectRqDto subjectRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(subjectRqDto);
        return facade.createSubject(subjectRqDto);
    }

    public ResponseDto<Void> deleteSubject(Subject subject) throws SQLException, ConstraintViolationException {
        if (findSubjectById(subject.getId()).getResult().isPresent()) {
            Validator.notNull(subject);
            return facade.deleteSubject(subject);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Subject not found"));
    }

    public ResponseDto<Subject> findSubjectById(UUID id) throws SQLException {
        return facade.findSubjectById(id);
    }

    public List<Subject> findAllSubjects() throws SQLException {
        return facade.findAllSubjects();
    }

    public ResponseDto<Subject> findSubjectByName(String name) throws SQLException {
        return facade.findSubjectByName(name);
    }
}
