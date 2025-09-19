package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ClassSubject;
import com.solvd.school.model.Student;
import java.util.List;

public interface IStudentsDAO extends IGenericDAO<Student> {

    List<Student> getByClassId(int classId);

    List<Student> getByGroupId(int groupId);

    List<Student> getAll();

}
