package com.neo.client.project.service;

import com.neo.client.project.model.response.LoginResponse;
import com.neo.client.project.model.response.ResponseDto;
import com.sbi.epay.authentication.model.AccessTokenRequest;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Main class description.
 *
 * <p>Provide a brief overview of the class and its purpose.
 * Include any additional documentation necessary.</p>
 *
 * @author RJ
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final AuthenticationService authenticationService;

    public ResponseDto<LoginResponse> login(AccessTokenRequest accessTokenRequest) {
        LoginResponse loginResponse = new LoginResponse(authenticationService.generateAccessToken(accessTokenRequest));
        return ResponseDto.<LoginResponse>builder().status(0).data(List.of(loginResponse)).build();
    }

    public ResponseDto<LoginResponse> getData() {
        EPayPrincipal ePayPrincipal = (EPayPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginResponse loginResponse = new LoginResponse(ePayPrincipal.getMid());
        return ResponseDto.<LoginResponse>builder().status(0).data(List.of(loginResponse)).build();
    }


}


