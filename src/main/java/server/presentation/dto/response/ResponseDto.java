package server.presentation.dto.response;

import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto<T> {
    private Optional<T> result = Optional.empty();
    private ErrorDto errorDto;

    public ResponseDto(ErrorDto errorDto) {
        this.result = Optional.empty();
        this.errorDto = errorDto;
    }
}
