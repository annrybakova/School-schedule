package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Classroom;
import com.solvd.school.model.SchoolClass;

import java.util.List;

public interface IClassDAO extends IGenericDAO<SchoolClass> {
    List<SchoolClass> getAll();
    Classroom getByName(String name);
}
