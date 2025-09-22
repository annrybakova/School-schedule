package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.dao.interfaces.IScheduleGenerationLogDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.model.ScheduleGenerationLog;
import com.solvd.school.service.interfaces.IScheduleService;
//import com.solvd.school.generator.BasicScheduleGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ScheduleServiceImpl implements IScheduleService {
    private static final Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);
    private final ILessonsDAO lessonsDAO;
    private final IScheduleGenerationLogDAO logDAO;
    //private final BasicScheduleGenerator generator;

    public ScheduleServiceImpl(ILessonsDAO lessonsDAO, IScheduleGenerationLogDAO logDAO) {
        this.lessonsDAO = lessonsDAO;
        this.logDAO = logDAO;
        //this.generator = generator;
    }

    @Override
    public void generateSchedule() {
        logger.info("Starting schedule generation...");
        //generator.generateBasicSchedule();
        logger.info("Schedule generation completed");
    }

    @Override
    public List<Lesson> getClassSchedule(int classId) {
        return lessonsDAO.getByClassAndDay(classId, 0); // 0 = all days
    }

    @Override
    public List<Lesson> getTeacherSchedule(int teacherId) {
        // Implementation needed
        return List.of();
    }

    @Override
    public List<Lesson> getClassroomSchedule(int classroomId) {
        // Implementation needed
        return List.of();
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
        // Implementation needed
        return null;
    }
}