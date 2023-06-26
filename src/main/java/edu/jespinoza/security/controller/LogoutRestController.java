package edu.jespinoza.security.controller;

import edu.jespinoza.exception.CustomMessage;
import edu.jespinoza.security.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("logoutRestController")
@RequestMapping(path = "/security")
@CrossOrigin(origins = "*")
@Slf4j
public class LogoutRestController {
    @Autowired
    @Qualifier("authService")
    private AuthService authService;

    @GetMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<CustomMessage> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            authService.logout(token);
        }
        log.info("Sesion cerrada con ëxito");
        CustomMessage message = new CustomMessage(HttpStatus.OK.value(), "Sesion cerrada con ëxito");
        return ResponseEntity.ok(message);
    }
}
