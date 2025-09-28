package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import com.solvd.school.model.Teacher;
import java.util.List;

public interface ITeacherService {
    Teacher getTeacherById(int id);

    List<Teacher> getAllTeachers();

    List<Teacher> getTeachersBySubject(int subjectId);

    List<Lesson> getTeacherSchedule(int teacherId);

    boolean isTeacherAvailable(int teacherId, int dayOfWeek, int lessonNumber);

    int getTeacherLessonsCount(int teacherId, int dayOfWeek);

    boolean isTeacherOverloaded(int teacherId, int dayOfWeek);
}