package com.solvd.school.dao.interfaces;

import com.solvd.school.model.TeacherAvailability;
import java.util.List;

public interface ITeacherAvailabilityDAO {
    List<TeacherAvailability> getByTeacher(int teacherId);

    void insert(TeacherAvailability availability);

    void update(TeacherAvailability availability);

    void delete(int id);
}
