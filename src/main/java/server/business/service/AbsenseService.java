package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Absense;
import server.data.repository.AbsenseRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.AbsenseCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbsenseService {
    private final AbsenseRepository absenseRepository;

    public Absense insertAbsence(Absense absense) {
        absenseRepository.save(absense);
        return absense;
    }

    public Absense findAttendanceById(UUID id) {
        return absenseRepository.findById(id).orElseThrow(()->new AbsenseCustomNotFoundException(id));
    }

    public Absense updateAttendance(Absense absense) {
        if (absenseRepository.existsDuplicateAbsence(absense.getLessonId(), absense.getPupilId(), absense.getIsPresent(), absense.getDate(), absense.getId())) {
            return absenseRepository.updateAttendance(absense.getId(), absense.getIsPresent());
        } else {
            throw new AbsenseCustomNotFoundException("Absense not found with ID: " + absense.getId());
        }
    }

    public Absense checkAttendance(UUID id) {
        return absenseRepository.findById(id).orElseThrow(() -> new AbsenseCustomNotFoundException("No attendance record found with ID: " + id));
    }

    public double calculateAttendance(UUID pupilId) {
        List<Absense> attendanceList = absenseRepository.findByPupilId(pupilId);

        int totalDays = attendanceList.size();
        int daysPresent = 0;

        for (Absense absense : attendanceList) {
            Boolean isPresent = absense.getIsPresent();
            if (isPresent != null && isPresent) {
                daysPresent++;
            }
        }

        return totalDays > 0 ? ((double) daysPresent / totalDays) * 100 : 0.0;
    }



}
