package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Student;
import java.util.List;

public interface IStudentsDAO {
    Student getById(int id);

    List<Student> getByClassId(int classId);

    List<Student> getByGroupId(int groupId);

    List<Student> getAll();

    void insert(Student student);

    void update(Student student);

    void delete(int id);
}
