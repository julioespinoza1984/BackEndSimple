package edu.jespinoza.security.dao.impl;

import edu.jespinoza.security.dao.UserDao;
import edu.jespinoza.security.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("userDao")
public class UserDaoImpl implements UserDao<User> {
    // Using an in-memory Map
    // to store the object data
    private Map<String, User> repository;

    public UserDaoImpl() {
        this.repository = new HashMap<>();
        User user = new User(1, "jespinoza", "abcd1234", "julioespinoza1984@gmail.com");
        repository.put(user.getUserName(), user);
    }

    @Override
    public User findByUserName(String userName) {
        return repository.get(userName);
    }
}
