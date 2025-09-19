package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Teacher;
import java.util.List;

public interface ITeachersDAO {
    Teacher getById(int id);

    List<Teacher> getAll();

    List<Teacher> getBySubject(int subjectId);

    void insert(Teacher teacher);

    void update(Teacher teacher);

    void delete(int id);
}
