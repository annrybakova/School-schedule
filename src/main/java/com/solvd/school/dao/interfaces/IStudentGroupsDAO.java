package com.solvd.school.dao.interfaces;

import java.util.List;

public interface IStudentGroupsDAO extends IGenericDAO<StudentGroup> {
    List<StudentGroup> getByClassId(int classId);

    List<StudentGroup> getAll();
}
