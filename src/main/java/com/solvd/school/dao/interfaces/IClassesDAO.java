package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;

public interface IClassesDAO extends IGenericDAO<ClassSubject> {
    ClassSubject getByName(String name);

    List<ClassSubject> getAll();

}
