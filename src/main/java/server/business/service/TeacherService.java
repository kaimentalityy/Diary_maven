package server.business.service;

import lombok.RequiredArgsConstructor;
import server.data.repository.TeacherProfileRepository;
import server.presentation.dto.request.CreateTeacherProfileRqDto;
import server.presentation.dto.response.TeacherProfileRespDto;

@RequiredArgsConstructor
public class TeacherService {

    private final TeacherProfileRepository teacherProfileRepository;

    public TeacherProfileRespDto createTeacherProfile(CreateTeacherProfileRqDto rqDto) {
        TeacherProfile teacherProfile = TeacherProfile.builder()
                .userId(rqDto.userId())
                .firstName(rqDto.firstName())
                .lastName(rqDto.lastName())
                .hireDate(rqDto.hireDate())
                .build();

        return TeacherProfileRespDto.fromEntity(teacherProfileRepository.save(teacherProfile));
    }

}
