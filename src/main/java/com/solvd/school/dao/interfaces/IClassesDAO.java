package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;

public interface IClassesDAO {
    ClassSubject getById(int id);

    ClassSubject getByName(String name);

    List<ClassSubject> getAll();

    void insert(ClassSubject cls);

    void update(ClassSubject cls);

    void delete(int id);
}
