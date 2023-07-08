package edu.jespinoza.security.service.impl;

import edu.jespinoza.basic.ActivateObjectService;
import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import edu.jespinoza.security.dao.UserDao;
import edu.jespinoza.security.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
@Slf4j
public class UserServiceImpl implements ActivateObjectService<User> {
    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void activate(long id, boolean active) throws ApplicationException, TechnicalException {
        User objectInDB = getById(id);
        objectInDB.setActive((short) (active ? 1 : 0));
    }

    @Override
    public void save(User user) throws ApplicationException, TechnicalException {
        if(user == null) {
            throw  new ApplicationException("Usuario no puede ser nulo");
        }
        if(user.getUserName() == null) {
            throw  new ApplicationException("El UserName del Usuario no puede ser nulo");
        }
        if(user.getUserName().isEmpty()) {
            throw  new ApplicationException("El UserName del Usuario no puede estar vacío");
        }
        if(user.getEmail() == null) {
            throw  new ApplicationException("El email del Usuario no puede ser nulo");
        }
        if(user.getEmail().isEmpty()) {
            throw  new ApplicationException("El email del Usuario no puede estar vacío");
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setActive((short) 1);
//        try {
//            dao.save(user);
//        } catch (Exception e) {
//            throw new TechnicalException(e.getMessage(), e);
//        }
    }

    @Override
    public void update(User user) throws ApplicationException, TechnicalException {
        if(user == null) {
            throw  new ApplicationException("Usuario no puede ser nulo");
        }
        if(user.getUserName() == null) {
            throw  new ApplicationException("El UserName del Usuario no puede ser nulo");
        }
        if(user.getUserName().isEmpty()) {
            throw  new ApplicationException("El UserName del Usuario no puede estar vacío");
        }
        if(user.getEmail() == null) {
            throw  new ApplicationException("El email del Usuario no puede ser nulo");
        }
        if(user.getEmail().isEmpty()) {
            throw  new ApplicationException("El email del Usuario no puede estar vacío");
        }
//        User objectInDB = getById(user.getId());
//        objectInDB.setRole(user.getRole());
    }

    @Override
    public User getById(long id) throws ApplicationException, TechnicalException {
        Optional<User> optional;
        try {
            optional = dao.findById(id);
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
        return optional.orElseThrow(() -> new ApplicationException("Usuario " + id + " no existe en BD"));
    }

    @Override
    public Collection<User> getPage(String filter, int page, int pageSize) throws TechnicalException {
        if (page <= 0) {
            page = 1;
        }
        Collection<User> collection;
        try {
            if (filter == null || filter.isEmpty()) {
                collection = dao.findAll(page, pageSize);
            } else {
                collection = dao.loadByEmail(filter, page, pageSize);
            }
            return collection.stream().sorted(Comparator.comparing(User::getUserName)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }

    @Override
    public int count() {
        return dao.count();
    }
}
