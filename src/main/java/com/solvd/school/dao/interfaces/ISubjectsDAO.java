package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Subject;
import java.util.List;

public interface ISubjectsDAO extends IGenericDAO<Subject> {
    List<Subject> getAll();

    List<Subject> getByClassId(int classId);

    int getLessonsPerWeek(int classId, int subjectId);
}
