package edu.jespinoza.security.service.impl;

import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import edu.jespinoza.security.dao.AuthResponseDao;
import edu.jespinoza.security.dto.AuthRequest;
import edu.jespinoza.security.dto.AuthResponse;
import edu.jespinoza.security.service.AuthService;
import edu.jespinoza.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("jwtService")
    private JwtService jwtService;
    @Autowired
    @Qualifier("authResponseDao")
    private AuthResponseDao dao;

    @Override
    public AuthResponse getAuthenticate(AuthRequest user) throws ApplicationException, TechnicalException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (Exception ex) {
            throw new ApplicationException("inavalid username/password");
        }
        String token = jwtService.generateToken(user.getUserName());
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        dao.add(response);
        return response;
    }

    @Override
    public void logout(String token) {
        dao.remove(token);
    }
}
