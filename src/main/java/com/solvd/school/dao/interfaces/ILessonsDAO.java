package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Lesson;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ILessonsDAO extends IGenericDAO<Lesson> {
    List<Lesson> getByClassAndDay(int classId, int dayOfWeek);

    int countAll(Lesson lesson);

    List<Lesson> getByClassroomAndDay(int classroomId, int dayOfWeek);

    List<Lesson> getByClass(int classId);

    List<Lesson> getByTeacher(int teacherId);

    List<Lesson> getByClassroom(int classroomId);

    List<Lesson> getByTeacherAndDay(int teacherId, int dayOfWeek);

}
