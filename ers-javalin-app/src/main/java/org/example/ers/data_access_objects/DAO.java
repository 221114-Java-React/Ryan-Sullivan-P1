package org.example.ers.data_access_objects;

import org.example.ers.models.User;

import java.util.List;

public interface DAO<T> {
    void create(T obj);
    User findById(String id);
    List<T> findAll();
    void update(T obj);
    void delete(String id);
}
