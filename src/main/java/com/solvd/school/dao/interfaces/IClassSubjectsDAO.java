package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;
import java.util.Map;

public interface IClassSubjectsDAO extends IGenericDAO<ClassSubject>{

    List<ClassSubject> getByClassId(int classId);

    ClassSubject getByClassAndSubject(int classId, int subjectId);

}
