package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Subject;
import server.data.repository.SubjectRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject) {
        subject.setId(UUID.randomUUID());
        return subjectRepository.save(subject);
    }

    public void deleteSubject(UUID id) {
        subjectRepository.deleteById(id);
    }

    public Optional<Subject> findSubjectById(UUID id) {
        return subjectRepository.findById(id);
    }

    public List<Subject> findAllSubjects() {
        return subjectRepository.findAllSubjects();
    }

    public Optional<Subject> findSubjectByName(String name) {
        return subjectRepository.findByName(name);
    }

    public boolean doesSubjectExist(UUID id) {
        return subjectRepository.doesSubjectExist(id);
    }
}