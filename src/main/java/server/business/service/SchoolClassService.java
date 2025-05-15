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

    public SchoolClass createClass(SchoolClass schoolClass) {
        schoolClass.setId(UUID.randomUUID());
        return schoolClassRepository.save(schoolClass);
    }

    public void deleteClass(UUID id) {
        schoolClassRepository.deleteClass(id);
    }

    public Optional<SchoolClass> findClassById(UUID id) {
        return schoolClassRepository.findClassById(id);
    }

    public boolean doesClassExist(UUID id) {
        return schoolClassRepository.doesSchoolClassExist(id);
    }
}
