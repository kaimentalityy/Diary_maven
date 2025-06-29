package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.DayOfWeek;
import server.data.repository.DayOfWeekRepository;
import server.utils.exception.notfound.DayOfWeekCustomNotFoundException;

@Service
@RequiredArgsConstructor
public class DayOfWeekService {

    private final DayOfWeekRepository repository;

    public DayOfWeek findDayOfWeekById(int value) {
        return repository.findById(value)
                .orElseThrow(() -> new DayOfWeekCustomNotFoundException(value));
    }
}

