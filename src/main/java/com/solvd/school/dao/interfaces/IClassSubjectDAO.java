package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;

public interface IClassSubjectDAO extends IGenericDAO<ClassSubject>{
    List<ClassSubject> getAll();
    Integer getRandomSubjectIdForClass(int classId);
    List<ClassSubject> getByClassId(int classId);
    List<ClassSubject> getBySubjectId(int subjectId);
    ClassSubject getByClassAndSubject(int classId, int subjectId);
    int getLessonsPerWeek(int classId, int subjectId);
}
