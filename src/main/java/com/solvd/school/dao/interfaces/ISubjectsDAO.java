package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Subject;
import java.util.List;

public interface ISubjectsDAO {
    Subject getById(int id);

    List<Subject> getAll();

    void insert(Subject subject);

    void update(Subject subject);

    void delete(int id);
}
