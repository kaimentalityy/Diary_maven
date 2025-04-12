package server.business.service;

import server.data.entity.Subject;
import server.data.repository.SubjectRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService() throws SQLException {
        subjectRepository = new SubjectRepository();
    }

    public Subject createSubject(Subject subject) throws SQLException {
        subject.setId(UUID.randomUUID());
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Subject subject) throws SQLException {
        subjectRepository.deleteById(subject.getId());
    }

    public Optional<Subject> findSubjectById(UUID id) throws SQLException {
        return subjectRepository.findById(id);
    }

    public List<Subject> findAllSubjects() throws SQLException {
        return subjectRepository.findAllSubjects();
    }

    public Optional<Subject> findSubjectByName(String name) throws SQLException {
        return subjectRepository.findByName(name);
    }
}