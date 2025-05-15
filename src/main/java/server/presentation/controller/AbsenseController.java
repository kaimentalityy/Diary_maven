package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Absense;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.utils.Validator;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/absences")
public class AbsenseController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<AbsenseRespDto> insertAbsence(@RequestBody AbsenseRqDto absenseRqDto) {
        Validator.notNull(absenseRqDto);
        AbsenseRespDto response = facade.insertAttendance(absenseRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAttendance(@PathVariable UUID id, @RequestParam Boolean isAbsent) {
        Validator.notNull(id);
        Validator.notNull(isAbsent);
        facade.updateAttendance(id, isAbsent);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> checkAttendance(@PathVariable UUID id) {
        Validator.notNull(id);
        Boolean exists = facade.checkAttendance(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/calculate")
    public ResponseEntity<Double> calculateAttendance(@RequestParam UUID userId, @RequestParam UUID classId) {
        Validator.notNull(userId);
        Validator.notNull(classId);
        Double attendancePercent = facade.calculateAttendancePercent(userId, classId);
        return ResponseEntity.ok(attendancePercent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absense> findAttendance(@PathVariable UUID id) {
        Validator.notNull(id);
        Absense absense = facade.findAttendance(id);
        return ResponseEntity.ok(absense);
    }
}

