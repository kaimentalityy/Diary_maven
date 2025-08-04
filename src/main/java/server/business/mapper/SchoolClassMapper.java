package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.SchoolClassRespDto;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teacherId", source = "teacher.id")
    SchoolClassRespDto toSchoolClassRespDto(SchoolClass schoolClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "year", source = "schoolClassRqDto.number")
    SchoolClass toSchoolClass(SchoolClassRqDto schoolClassRqDto, User teacher);
}
