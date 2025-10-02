package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.dao.interfaces.ISpecialClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.LessonDAO;
import com.solvd.school.dao.mybatisimpl.SpecialClassroomDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.ILessonService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LessonServiceImpl implements ILessonService {
    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);
    private final static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    private static final ILessonsDAO lessonsDAO = new LessonDAO(sqlSessionFactory);

    public LessonServiceImpl() {}

    @Override
    public Lesson getLessonById(int id) {
        return lessonsDAO.getById(id);
    }

    @Override
    public List<Lesson> getLessonsByClass(int classId) {
        return lessonsDAO.getByClass(classId);
    }

    @Override
    public List<Lesson> getLessonsByClassAndDay(int classId, int dayOfWeek) {
        return lessonsDAO.getByClassAndDay(classId, dayOfWeek);
    }

    @Override
    public void createLesson(Lesson lesson) {
        lessonsDAO.insert(lesson);
    }

    @Override
    public void updateLesson(Lesson lesson) {
        lessonsDAO.update(lesson);
    }

    @Override
    public void deleteLesson(int id) {
        lessonsDAO.delete(id);
    }

    @Override
    public boolean isTimeSlotOccupied(int classId, int dayOfWeek, int lessonNumber) {
        List<Lesson> classLessons = lessonsDAO.getByClassAndDay(classId, dayOfWeek);
        return classLessons.stream()
                .anyMatch(lesson -> lesson.getLessonNumber() == lessonNumber);
    }
}