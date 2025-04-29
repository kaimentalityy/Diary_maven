package server.presentation.dto.response;

import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto<T> {
    private Optional<T> result;
    private ErrorDto errorDto;

    public ResponseDto(ErrorDto errorDto) {
        this.result = Optional.empty();  // No result
        this.errorDto = errorDto;
    }
}
