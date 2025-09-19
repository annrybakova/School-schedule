package com.solvd.school.dao.interfaces;

import java.util.List;

public interface IGenericDAO<T> {
    T getById(int id);

    // List<T> getAll();
    void insert(T entity);

    void update(T entity);

    void delete(int id);
}
