package com.revature.ers.data_access_objects;

import com.revature.ers.models.User;

import java.util.List;

public interface DAO<T> {
    void create(T obj);
    User findById(String id);
    List<T> findAll();
    void update(T obj);
    void delete(String id);
}
