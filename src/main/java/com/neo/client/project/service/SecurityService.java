package com.neo.client.project.service;

import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.AuthenticationUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityService implements AuthenticationUserService {
    @Override
    public Optional<EPayPrincipal> loadUserByUserName(String s) {
        EPayPrincipal authenticateEntity = new EPayPrincipal();
        authenticateEntity.setAuthenticationKey("232323");
        authenticateEntity.setUserRole(List.of("Admin"));
        return Optional.of(authenticateEntity);
    }
}
