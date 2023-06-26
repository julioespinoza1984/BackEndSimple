package edu.jespinoza.security.dao.impl;

import edu.jespinoza.security.dao.AuthResponseDao;
import edu.jespinoza.security.dto.AuthResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("authResponseDao")
public class AuthResponseDaoImpl implements AuthResponseDao {
    private final Map<String, AuthResponse> map = new HashMap<>();

    @Override
    public void add(AuthResponse response) {
        map.put(response.getToken(), response);
    }

    @Override
    public AuthResponse get(String token) {
        return map.get(token);
    }

    @Override
    public void remove(String token) {
        map.remove(token);
    }
}
