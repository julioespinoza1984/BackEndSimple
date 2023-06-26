package edu.jespinoza.security.service;

import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import edu.jespinoza.security.dto.AuthRequest;
import edu.jespinoza.security.dto.AuthResponse;

public interface AuthService {
    AuthResponse getAuthenticate(AuthRequest user) throws ApplicationException, TechnicalException;

    void logout(String token);
}
