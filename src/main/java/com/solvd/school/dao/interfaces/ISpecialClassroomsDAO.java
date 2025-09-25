package com.solvd.school.dao.interfaces;

import com.solvd.school.model.SpecialClassroom;
import java.util.List;

public interface ISpecialClassroomsDAO {
    SpecialClassroom getById(int id);

    List<SpecialClassroom> getBySubjectId(int subjectId);

    void insert(SpecialClassroom sc);

    void delete(int id);

    Integer getClassroomIdBySubjectId(int subjectId);
}
