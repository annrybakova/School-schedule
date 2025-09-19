package com.solvd.school.dao.interfaces;

import com.solvd.school.model.StudentGroup;
import java.util.List;

public interface IStudentGroupsDAO {
    StudentGroup getById(int id);

    List<StudentGroup> getByClassId(int classId);

    List<StudentGroup> getAll();

    void insert(StudentGroup group);

    void update(StudentGroup group);

    void delete(int id);
}
