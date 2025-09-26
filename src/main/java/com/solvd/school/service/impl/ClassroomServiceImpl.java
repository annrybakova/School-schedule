package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassroomsDAO;
import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.dao.interfaces.ISpecialClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.ClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.LessonDAO;
import com.solvd.school.dao.mybatisimpl.SpecialClassroomDAO;
import com.solvd.school.model.Classroom;
import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomServiceImpl implements IClassroomService {
    private static final Logger logger = LogManager.getLogger(ClassroomServiceImpl.class);
    private final static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    private final IClassroomsDAO classroomsDAO = new ClassroomsDAO(sqlSessionFactory);
    private final ILessonsDAO lessonsDAO = new LessonDAO(sqlSessionFactory);
    private final ISpecialClassroomsDAO specialClassroomsDAO = new SpecialClassroomDAO(sqlSessionFactory);

    public ClassroomServiceImpl() {
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
                .collect(Collectors.toList());
    }

    @Override
    public boolean isClassroomAvailable(int classroomId, int dayOfWeek, int lessonNumber) {
        // List<Lesson> classroomLessons = lessonsDAO.getByClassAndDay(0, dayOfWeek); // Need proper implementation
        // return classroomLessons.stream()
        //         .noneMatch(lesson -> lesson.getClassroomId() == classroomId &&
        //                 lesson.getLessonNumber() == lessonNumber);
        List<Lesson> lessonsInRoom = lessonsDAO.getByClassroomAndDay(classroomId, dayOfWeek);
        return lessonsInRoom.stream()
                .noneMatch(lesson -> lesson.getLessonNumber() == lessonNumber);

    }

    @Override
    public Classroom getCommonClassroomForSubject(int subjectId) {
        Classroom classroom = classroomsDAO.getCommonClassroom(subjectId);
        if (classroom == null) {
            logger.warn("No common classroom found for subjectId={}", subjectId);
            return null;
        }
        return classroom;
    }

    @Override
    public Classroom getSpecialClassroomForSubject(int subjectId) {
        // Implementation for special classrooms (physics, chemistry labs)
        // return getAllClassrooms().stream()
        // .filter(Classroom::isSpecial)
        // .findFirst()
        // .orElse(null);
        Integer classroomId = specialClassroomsDAO.getClassroomIdBySubjectId(subjectId);
        if (classroomId == null) {
            logger.warn("No special classroom found for subjectId={}", subjectId);
            return null;
        }
        return classroomsDAO.getById(classroomId);
    }

    public boolean hasEnoughCapacity(int classroomId, int studentCount) {
        Classroom classroom = classroomsDAO.getById(classroomId);
        if (classroom == null) {
            logger.error("Classroom with id={} not found!", classroomId);
            return false;
        }
        boolean ok = studentCount <= classroom.getCapacity();
        if (!ok) {
            logger.warn("Classroom {} capacity exceeded: {} students > capacity {}",
                    classroomId, studentCount, classroom.getCapacity());
        }
        return ok;
    }
}