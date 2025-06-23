package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.SchoolClass;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.SchoolClassRespDto;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    SchoolClassRespDto toSchoolClassRespDto(SchoolClass schoolClass);

    SchoolClass toSchoolClass(SchoolClassRqDto schoolClassRqDto);
}
