package org.example.ers.data_access_objects;

import java.util.List;

public interface DAO<T> {
    void create(T obj);
    List<T> findById(String id);
    List<T> findAll();
    void update(T obj);
    void delete(String id);
}
