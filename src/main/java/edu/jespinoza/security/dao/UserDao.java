package edu.jespinoza.security.dao;

import edu.jespinoza.security.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    void save(User user);

    Optional<User> findById(long id);

    User findByUserName(String userName);

    User findByEmail(String email);

    Collection<User> findAll(int page, int pageSize);

    Collection<User> loadByEmail(String email, int page, int pageSize);

    int count();
}