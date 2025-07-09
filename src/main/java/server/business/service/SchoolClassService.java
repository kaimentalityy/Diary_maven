package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import server.business.mapper.SchoolClassMapper;
import server.business.mapper.UserMapper;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.data.repository.SchoolClassRepository;
import server.data.repository.UserRepository;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.SchoolClassRespDto;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.sql.SQLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;
    private final UserRepository userRepository;
    // TODO insert the Service bean here, instead of the Repository(Circular dependency)

    public SchoolClassRespDto createSchoolClass(SchoolClassRqDto schoolClassRqDto) {

        User user = userRepository.findById(schoolClassRqDto.teacherId()).orElseThrow(() -> new UserCustomNotFoundException("User not found"));
        SchoolClass schoolClass = schoolClassMapper.toSchoolClass(schoolClassRqDto, user);

        schoolClass = createClass(schoolClass);

        return schoolClassMapper.toSchoolClassRespDto(schoolClass);
    }

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
