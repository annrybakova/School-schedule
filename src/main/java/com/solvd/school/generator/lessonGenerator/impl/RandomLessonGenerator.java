package com.solvd.school.generator.lessonGenerator.impl;

import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.dao.interfaces.IClassroomsDAO;
import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.ClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.TeacherDAO;
import com.solvd.school.generator.lessonGenerator.interfaces.ILessonGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.util.MyBatisUtil;
import com.solvd.school.util.ScheduleConstants;

import java.util.concurrent.ThreadLocalRandom;

public class RandomLessonGenerator implements ILessonGenerator {

    @Override
    public Lesson getLessonFor(int classId, int lessonNumber, int dayNumber) {
        IClassSubjectDAO classSubjectDAO = new ClassSubjectDAO(MyBatisUtil.getSqlSessionFactory());
        int subjectId = classSubjectDAO.getRandomSubjectIdForClass(classId);

        ITeachersDAO teachersDAO = new TeacherDAO(MyBatisUtil.getSqlSessionFactory());
        int teacherId = teachersDAO.getTeacherForSubject(subjectId);

        IClassroomsDAO classroomsDAO = new ClassroomsDAO(MyBatisUtil.getSqlSessionFactory());
        int classroomId = classroomsDAO.getRandomClassroomId(subjectId);

        return new Lesson(classId, subjectId, teacherId, classroomId, dayNumber, lessonNumber);
    }
}