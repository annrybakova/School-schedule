package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.dao.interfaces.IScheduleGenerationLogDAO;
import com.solvd.school.dao.mybatisimpl.LessonDAO;
import com.solvd.school.dao.mybatisimpl.ScheduleGenerationLogDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.model.ScheduleGenerationLog;
import com.solvd.school.service.interfaces.IScheduleService;
//import com.solvd.school.generator.BasicScheduleGenerator;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ScheduleServiceImpl implements IScheduleService {
    private static final Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);
    private final static SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    private static final ILessonsDAO lessonsDAO = new LessonDAO(sqlSessionFactory);
    private static final IScheduleGenerationLogDAO logDAO = new ScheduleGenerationLogDAO(sqlSessionFactory);

    public ScheduleServiceImpl() {}

    @Override
    public void generateSchedule() {
        logger.info("Starting schedule generation...");
        logger.info("Schedule generation completed");
    }

    @Override
    public List<Lesson> getClassSchedule(int classId) {
        return lessonsDAO.getByClass(classId);
    }

    @Override
    public List<Lesson> getTeacherSchedule(int teacherId) {
        return lessonsDAO.getByTeacher(teacherId);
    }

    @Override
    public List<Lesson> getClassroomSchedule(int classroomId) {
        return lessonsDAO.getByClassroom(classroomId);
    }

    @Override
    public boolean saveGeneratedSchedule(List<Lesson> lessons) {
        try {
            for (Lesson lesson : lessons) {
                lessonsDAO.insert(lesson);
            }
            return true;
        } catch (Exception e) {
            logger.error("Failed to save schedule", e);
            return false;
        }
    }

    @Override
    public ScheduleGenerationLog getLastGenerationLog() {
        return logDAO.getLastLog();
    }
}