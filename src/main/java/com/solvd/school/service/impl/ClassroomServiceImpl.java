package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassroomsDAO;
import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.model.Classroom;
import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.IClassroomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClassroomServiceImpl implements IClassroomService {
    private static final Logger logger = LogManager.getLogger(ClassroomServiceImpl.class);
    private final IClassroomsDAO classroomsDAO;
    private final ILessonsDAO lessonsDAO;

    public ClassroomServiceImpl(IClassroomsDAO classroomsDAO, ILessonsDAO lessonsDAO) {
        this.classroomsDAO = classroomsDAO;
        this.lessonsDAO = lessonsDAO;
    }

    @Override
    public Classroom getClassroomById(int id) {
        return classroomsDAO.getById(id);
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomsDAO.getAll();
    }

    @Override
    public List<Classroom> getAvailableClassrooms(int dayOfWeek, int lessonNumber) {
        List<Classroom> allClassrooms = classroomsDAO.getAll();
        return allClassrooms.stream()
                .filter(classroom -> isClassroomAvailable(classroom.getId(), dayOfWeek, lessonNumber))
                .toList();
    }

    @Override
    public boolean isClassroomAvailable(int classroomId, int dayOfWeek, int lessonNumber) {
        List<Lesson> classroomLessons = lessonsDAO.getByClassAndDay(0, dayOfWeek); // Need proper implementation
        return classroomLessons.stream()
                .noneMatch(lesson -> lesson.getClassroomId() == classroomId &&
                        lesson.getLessonNumber() == lessonNumber);
    }

    @Override
    public Classroom getSpecialClassroomForSubject(int subjectId) {
        // Implementation for special classrooms (physics, chemistry labs)
        return getAllClassrooms().stream()
                .filter(Classroom::isSpecial)
                .findFirst()
                .orElse(null);
    }
}