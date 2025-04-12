package server.presentation.dto.response;

import java.util.Optional;

public class ResponseDto<T> {
    private Optional<T> result;
    private ErrorDto errorDto;

    public ResponseDto(Optional<T> result, ErrorDto errorDto) {
        this.result = result;
        this.errorDto = errorDto;
    }

    public ResponseDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    public Optional<T> getResult() {
        return result;
    }

    public void setResult(Optional<T> result) {
        this.result = result;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "result=" + result +
                ", errorDto=" + errorDto +
                '}';
    }
}
