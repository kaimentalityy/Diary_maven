package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Absense;
import server.data.entity.User;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/absences")
public class AbsenseController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<AbsenseRespDto> insertAbsence(@RequestBody AbsenseRqDto absenseRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(absenseRqDto);

        return facade.insertAttendance(absenseRqDto);
    }

    @PatchMapping("/{id}")
    public ResponseDto<Void> updateAttendance(@PathVariable UUID id, @RequestParam Boolean isAbsent) throws SQLException, ConstraintViolationException {

        ResponseDto<Absense> absense = findAttendance(id);

        if (absense.getResult().isPresent()) {
            Validator.notNull(absense);
            return facade.updateAttendance(absense.getResult().orElse(null), isAbsent);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Attendance not found"));
    }

    @PostMapping("/check")
    public Boolean checkAttendance(@PathVariable UUID id) throws SQLException {
        return facade.checkAttendance(findAttendance(id).getResult().orElse(null));
    }

    @GetMapping()
    public ResponseDto<Double> calculateAttendance(@RequestParam UUID userId, @RequestParam UUID classId) throws SQLException {
        return facade.calculateAttendancePercent(facade.findUserById(userId).getResult().orElse(null), classId);
    }

    @GetMapping("/{id}")
    public ResponseDto<Absense> findAttendance(@PathVariable UUID id) throws SQLException {
        return facade.findAttendance(id);
    }
}
