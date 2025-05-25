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
        subject.setId(UUID.randomUUID());
        try {
            return subjectRepository.save(subject);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to create subject");
        }
    }

    public void deleteSubject(UUID id) {
        try {
            if (subjectRepository.doesSubjectExist(id)) {
                subjectRepository.deleteById(id);
            } else {
                throw new SubjectCustomNotFoundException("Subject not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete subject with ID: " + id);
        }
    }

    public Subject findSubjectById(UUID id) {
        try {
            return subjectRepository.findById(id)
                    .orElseThrow(() -> new SubjectCustomNotFoundException("Subject not found with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find subject with ID: " + id);
        }
    }

    public List<Subject> findAllSubjects() {
        try {
            return subjectRepository.findAllSubjects();
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to fetch all subjects");
        }
    }

    public Subject findSubjectByName(String name) {
        try {
            return subjectRepository.findByName(name)
                    .orElseThrow(() -> new SubjectCustomNotFoundException("Subject not found with name: " + name));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find subject by name: " + name);
        }
    }
}
