package edu.jespinoza.security.dao;

import edu.jespinoza.security.dto.AuthResponse;

public interface AuthResponseDao {
    void add(AuthResponse response);

    AuthResponse get(String token);

    void remove(String token);
}
