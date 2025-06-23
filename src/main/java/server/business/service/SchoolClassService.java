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
        return schoolClassRepository.save(schoolClass);
    }

    public void deleteClass(UUID id) {
        if (findClassById(id) == null) {
            throw new SchoolClassCustomNotFoundException("Class not found with ID: " + id);
        } else {
            schoolClassRepository.deleteById(id);
        }
    }

    public SchoolClass findClassById(UUID id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassCustomNotFoundException("Class not found with ID: " + id));
    }
}
