package com.neo.client.project.controller;

import com.neo.client.project.model.response.LoginResponse;
import com.neo.client.project.model.response.ResponseDto;
import com.neo.client.project.service.LoginService;
import com.sbi.epay.authentication.model.AccessTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login API")
    public ResponseDto<LoginResponse> login(@RequestBody AccessTokenRequest accessTokenRequest) {
        log.info("Received request for login {}", accessTokenRequest);
        return loginService.login(accessTokenRequest);
    }

    @GetMapping
    @Operation(summary = "Get Login API")
    public ResponseDto<LoginResponse> getData() {
        return loginService.getData();
    }

}
