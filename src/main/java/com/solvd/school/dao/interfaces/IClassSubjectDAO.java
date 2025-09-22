package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import java.util.List;

public interface IClassSubjectDAO extends IGenericDAO<ClassSubject>{
    List<ClassSubject> getAll();

    Integer getRandomSubjectIdForClass(int classId);
}
