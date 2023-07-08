package edu.jespinoza.security.dao.impl;

import edu.jespinoza.security.dao.UserDao;
import edu.jespinoza.security.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    // Using an in-memory Map
    // to store the object data
    private final Map<Long, User> repository;

    public UserDaoImpl() {
        this.repository = new HashMap<>();
        User admin = new User(1, "admin", "$2a$10$v1gN4gOCTCV4g0IvshgqMOwlWqhe9KUrmW670xLnYs.ZvOuJ8gf.i",
                "admin2023@demo.com", (short) 1, "Administrador");
        User user = new User(2, "user", "$2a$10$hx5KNWEWI7NVs6xh0nZfNO1jInqPqE69LvTq2cFrszABmkrw7eiqa",
                "user2023@demo.com", (short) 1, "Usuario");
        repository.put(admin.getId(), admin);
        repository.put(user.getId(), user);
    }

    @Override
    public void save(User user) {
        repository.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public User findByUserName(String userName) {
        Collection<User> collection =
            repository.values().stream().filter(u -> u.getUserName().equals(userName)).collect(Collectors.toList());
        if(collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }

    @Override
    public User findByEmail(String email) {
        Collection<User> collection =
                repository.values().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
        if(collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }

    @Override
    public Collection<User> findAll(int page, int pageSize) {
        List<User> list = new ArrayList<>(repository.values());
        int begin = pageSize * (page - 1);
        int end = pageSize * page;
        if(list.size() < end) {
            end = list.size();
        }
        return list.subList(begin, end);
    }

    @Override
    public Collection<User> loadByEmail(String email, int page, int pageSize) {
        List<User> list =
                repository.values().stream().filter(u -> u.getEmail().startsWith(email)).collect(Collectors.toList());
        int begin = pageSize * (page - 1);
        int end = pageSize * page;
        if(list.size() < end) {
            end = list.size();
        }
        return list.subList(begin, end);
    }

    @Override
    public int count() {
        return repository.size();
    }
}