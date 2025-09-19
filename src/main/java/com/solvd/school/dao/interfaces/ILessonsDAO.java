package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Lesson;
import java.util.List;

public interface ILessonsDAO {
    Lesson getById(int id);

    List<Lesson> getByClassAndDay(int classId, int dayOfWeek);

    void insert(Lesson lesson);

    void update(Lesson lesson);

    void delete(int id);
}
