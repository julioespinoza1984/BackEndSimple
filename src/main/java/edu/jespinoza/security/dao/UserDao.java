package edu.jespinoza.security.dao;

public interface UserDao<T>{
    T findByUserName(String userName);
}