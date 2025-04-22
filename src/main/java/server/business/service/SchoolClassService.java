package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.SchoolClass;
import server.data.repository.SchoolClassRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClass createClass(SchoolClass schoolClass) throws SQLException {
        schoolClass.setId(UUID.randomUUID());
        return schoolClassRepository.save(schoolClass);
    }

    public void deleteClass(SchoolClass schoolClass) throws SQLException {
        schoolClassRepository.deleteClass(schoolClass.getId());
    }

    public Optional<SchoolClass> findClassById(UUID id) throws SQLException {
        return schoolClassRepository.findClassById(id);
    }
}
