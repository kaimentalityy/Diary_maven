package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Subject;
import server.data.repository.SubjectRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.SubjectCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(UUID id) {
        if (findById(id) == null) {
            throw new SubjectCustomNotFoundException("Subject not found with ID: " + id);
        } else {
            subjectRepository.deleteById(id);
        }
    }

    public Subject findById(UUID id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectCustomNotFoundException("Subject not found with ID: " + id));
    }

    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject findSubjectByName(String name) {
        return subjectRepository.findByName(name)
                .orElseThrow(() -> new SubjectCustomNotFoundException("Subject not found with name: " + name));
    }
}
