package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Lesson;

import java.util.List;

public interface ILessonsDAO extends IGenericDAO<Lesson> {
    List<Lesson> getByClassAndDay(int classId, int dayOfWeek);

    int countAll(Lesson lesson);
}
