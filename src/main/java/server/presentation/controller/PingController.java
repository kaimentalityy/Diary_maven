package server.presentation.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping("/pingSimple")
    public String ping() {
        return "pong";
    }

    @GetMapping("/test-optional")
    public ResponseDto<String> testOptionalResponse() {
        return new ResponseDto<>(Optional.of("Hello from Optional!"), null);
    }

    @GetMapping("/pingSimple1")
    public String ping1() {
        return "pong1";
    }

    @PostConstruct
    public void init() {
        System.out.println("PingController initialized");
    }


}
