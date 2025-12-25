package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import server.data.entity.SchoolClass;
import server.data.entity.StudentProfile;
import server.data.entity.User;
import server.presentation.dto.request.CreateStudentProfileRqDto;
import server.presentation.dto.request.UpdateStudentProfileRqDto;
import server.presentation.dto.response.StudentProfileRespDto;

@Mapper(componentModel = "spring")
public interface StudentProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "schoolClass", source = "schoolClass")
    @Mapping(source = "dto.firstName", target = "firstName")
    @Mapping(source = "dto.lastName", target = "lastName")
    @Mapping(source = "dto.birthDate", target = "birthDate")
    @Mapping(source = "dto.enrollmentDate", target = "enrollmentDate")
    StudentProfile toEntity(CreateStudentProfileRqDto dto, User user, SchoolClass schoolClass);


    @Mapping(source = "schoolClass.id", target = "classId")
    StudentProfileRespDto toDto(StudentProfile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "schoolClass", source = "schoolClass")
    @Mapping(source = "dto.firstName", target = "firstName")
    @Mapping(source = "dto.lastName", target = "lastName")
    @Mapping(source = "dto.birthDate", target = "birthDate")
    @Mapping(source = "dto.enrollmentDate", target = "enrollmentDate")
    void updateEntity(UpdateStudentProfileRqDto dto, SchoolClass schoolClass, @MappingTarget StudentProfile entity);
}