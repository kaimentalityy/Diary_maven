package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Subject;
import server.data.entity.User;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    TeacherRespDto toTeacherRespDto(TeacherOfSubject teacherOfSubject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", source = "subjectId", qualifiedByName = "mapSubject")
    @Mapping(target = "teacher", source = "teacherId", qualifiedByName = "mapUser")
    TeacherOfSubject toTeacher(TeacherRqDto teacherRqDto);

    @Mapping(target = "subject", source = "subjectId", qualifiedByName = "mapSubject")
    @Mapping(target = "teacher", source = "teacherId", qualifiedByName = "mapUser")
    TeacherOfSubject toTeacherForUpdate(UpdateTeacherRqDto updateTeacherRqDto);

    @Named("mapSubject")
    default Subject mapSubject(UUID subjectId) {
        if (subjectId == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = new Subject();
        subject.setId(subjectId);
        return subject;
    }

    @Named("mapUser")
    default User mapUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}