package com.solvd.school.generator.lessonGenerator.impl;

import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.TeacherDAO;
import com.solvd.school.generator.lessonGenerator.interfaces.ILessonGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.service.impl.ClassroomServiceImpl;
import com.solvd.school.service.impl.SubjectServiceImpl;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.util.MyBatisUtil;

public class RandomLessonGenerator implements ILessonGenerator {

    @Override
    public Lesson getLessonFor(int classId, int lessonNumber, int dayNumber) {
        IClassSubjectDAO classSubjectDAO = new ClassSubjectDAO(MyBatisUtil.getSqlSessionFactory());
        int subjectId = classSubjectDAO.getRandomSubjectIdForClass(classId);

        ITeachersDAO teachersDAO = new TeacherDAO(MyBatisUtil.getSqlSessionFactory());
        int teacherId = teachersDAO.getTeacherForSubject(subjectId);

        int classroomId = getRandomClassroomIdFor(subjectId);

        return new Lesson(classId, subjectId, teacherId, classroomId, dayNumber, lessonNumber);
    }

    private int getRandomClassroomIdFor(int subjectId) {
        IClassroomService classroomService = new ClassroomServiceImpl();
        ISubjectService subjectService = new SubjectServiceImpl();

        int randomClassroomId;
        if(subjectService.requiresSpecialRoom(subjectId)) {
            randomClassroomId = classroomService.getSpecialClassroomForSubject(subjectId).getId();
        }
        else {
            randomClassroomId = classroomService.getCommonClassroomForSubject(subjectId).getId();
        }

        return randomClassroomId;
    }
}