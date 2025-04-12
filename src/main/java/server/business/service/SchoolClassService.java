package server.business.service;

import server.data.entity.SchoolClass;
import server.data.repository.SchoolClassRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassService() throws SQLException {
        schoolClassRepository = new SchoolClassRepository();
    }

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
