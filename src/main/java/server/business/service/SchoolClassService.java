package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.SchoolClass;
import server.data.repository.SchoolClassRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;

import java.sql.SQLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClass createClass(SchoolClass schoolClass) {
        schoolClass.setId(UUID.randomUUID());
        try {
            return schoolClassRepository.save(schoolClass);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to create school class");
        }
    }

    public void deleteClass(UUID id) {
        try {
            if (schoolClassRepository.doesSchoolClassExist(id)) {
                schoolClassRepository.deleteClass(id);
            } else {
                throw new SchoolClassCustomNotFoundException("Class not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete school class with ID: " + id);
        }
    }

    public SchoolClass findClassById(UUID id) {
        try {
            return schoolClassRepository.findClassById(id)
                    .orElseThrow(() -> new SchoolClassCustomNotFoundException("Class not found with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find school class with ID: " + id);
        }
    }
}
