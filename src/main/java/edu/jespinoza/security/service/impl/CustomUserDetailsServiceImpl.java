package edu.jespinoza.security.service.impl;

import edu.jespinoza.security.dao.UserDao;
import edu.jespinoza.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), new ArrayList<>());
    }
}
