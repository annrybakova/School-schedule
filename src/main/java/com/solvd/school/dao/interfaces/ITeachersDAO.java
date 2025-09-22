package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Teacher;

import java.util.List;

public interface ITeachersDAO extends IGenericDAO<Teacher> {
    List<Teacher> getAll();

    List<Teacher> getBySubject(int subjectId);

    Integer getTeacherForSubject(int subjectId);
}
