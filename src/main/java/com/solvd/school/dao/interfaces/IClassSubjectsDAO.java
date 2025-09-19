package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;
import java.util.Map;

public interface IClassSubjectsDAO {
    ClassSubject getById(int id);

    List<ClassSubject> getByClassId(int classId);

    ClassSubject getByClassAndSubject(int classId, int subjectId);

    void insert(ClassSubject cs);

    void update(ClassSubject cs);

    void delete(int id);
}
