package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Classroom;

import java.util.List;

public interface IClassroomsDAO extends IGenericDAO<Classroom> {
    List<Classroom> getAll();

    Integer getRandomClassroomId(int subjectId);
}
