package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.request.AttendancePercentageRequest;
import server.presentation.dto.request.UpdateAbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.AttendancePercentageResponse;
import server.presentation.dto.response.CheckAttendanceRespDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/absences")
public class AbsenseController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AbsenseRespDto insertAbsence(@Valid @RequestBody AbsenseRqDto absenseRqDto) {
        return facade.insertAbsense(absenseRqDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public AbsenseRespDto updateAttendance(@Valid @RequestBody UpdateAbsenseRqDto updateAbsenseRqDto) {
        return facade.updateAttendance(updateAbsenseRqDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check/{id}")
    public CheckAttendanceRespDto checkAttendance(@PathVariable UUID id) {
        return facade.checkAttendance(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/calculate")
    public AttendancePercentageResponse calculateAttendance(@Valid @RequestBody AttendancePercentageRequest request) {
        return facade.calculateAttendancePercent(request);
    }



}

