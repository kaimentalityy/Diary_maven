package server.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping("/pingSimple")
    public String ping() {
        return "nicoletti";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/test-optional")
    public String testOptionalResponse() {
        return "test-optional";
    }

    @GetMapping("/pingSimple1")
    public String ping1() {
        return "pong1";
    }
}
