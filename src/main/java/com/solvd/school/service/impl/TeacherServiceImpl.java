package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.model.Teacher;
import com.solvd.school.service.interfaces.ITeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TeacherServiceImpl implements ITeacherService {
    private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);
    private final ITeachersDAO teachersDAO;
    private final ILessonsDAO lessonsDAO;

    public TeacherServiceImpl(ITeachersDAO teachersDAO, ILessonsDAO lessonsDAO) {
        this.teachersDAO = teachersDAO;
        this.lessonsDAO = lessonsDAO;
    }

    @Override
    public Teacher getTeacherById(int id) {
        return teachersDAO.getById(id);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teachersDAO.getAll();
    }

    @Override
    public List<Teacher> getTeachersBySubject(int subjectId) {
        return teachersDAO.getBySubject(subjectId);
    }

    @Override
    public List<Lesson> getTeacherSchedule(int teacherId) {
        return lessonsDAO.getByTeacher(teacherId);
    }

    @Override
    public boolean isTeacherAvailable(int teacherId, int dayOfWeek, int lessonNumber) {
        // List<Lesson> teacherLessons = lessonsDAO.getByClassAndDay(0, dayOfWeek); //
        // Need proper implementation
        // return teacherLessons.stream()
        // .noneMatch(lesson -> lesson.getTeacherId() == teacherId &&
        // lesson.getLessonNumber() == lessonNumber);
        List<Lesson> teacherLessons = lessonsDAO.getByTeacherAndDay(teacherId, dayOfWeek);
        return teacherLessons.stream()
                .noneMatch(lesson -> lesson.getLessonNumber() == lessonNumber);
    }

    @Override
    public int getTeacherLessonsCount(int teacherId, int dayOfWeek) {
        // List<Lesson> teacherLessons = lessonsDAO.getByClassAndDay(0, dayOfWeek); //
        // Need proper implementation
        // return (int) teacherLessons.stream()
        // .filter(lesson -> lesson.getTeacherId() == teacherId)
        // .count();
        List<Lesson> teacherLessons = lessonsDAO.getByTeacherAndDay(teacherId, dayOfWeek);
        return teacherLessons.size();
    }

    @Override
    public boolean isTeacherOverloaded(int teacherId, int dayOfWeek) {
        Teacher teacher = getTeacherById(teacherId);
        int actualLessons = getTeacherLessonsCount(teacherId, dayOfWeek);
        return actualLessons > teacher.getMaxLessonsPerDay();
    }
}