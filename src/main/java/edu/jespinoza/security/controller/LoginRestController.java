package edu.jespinoza.security.controller;

import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import edu.jespinoza.security.dto.AuthRequest;
import edu.jespinoza.security.dto.AuthResponse;
import edu.jespinoza.security.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("loginRestController")
@Slf4j
public class LoginRestController {
    @Autowired
    @Qualifier("authService")
    private AuthService authService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user)
            throws ApplicationException, TechnicalException {
        log.info("Entrando a login");
        AuthResponse response = authService.getAuthenticate(user);
        return ResponseEntity.ok(response);
    }
}
