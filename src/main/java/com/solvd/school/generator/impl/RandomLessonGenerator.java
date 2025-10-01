package com.solvd.school.generator.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.TeacherDAO;
import com.solvd.school.generator.interfaces.ILessonGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.service.impl.ClassroomServiceImpl;
import com.solvd.school.service.impl.SubjectServiceImpl;
import com.solvd.school.service.impl.TeacherServiceImpl;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.service.interfaces.ITeacherService;
import com.solvd.school.util.MyBatisUtil;

public class RandomLessonGenerator implements ILessonGenerator {
    private static final int MAX_ATTEMPTS = 10;

    @Override
    public Lesson getLessonFor(int classId, int lessonNumber, int dayNumber) {
        IClassSubjectDAO classSubjectDAO = new ClassSubjectDAO(MyBatisUtil.getSqlSessionFactory());
        ITeacherService teacherService = new TeacherServiceImpl();
        IClassroomService classroomService = new ClassroomServiceImpl();

        int subjectId = classSubjectDAO.getRandomSubjectIdForClass(classId);

        int teacherId = findAvailableTeacher(subjectId, dayNumber, lessonNumber, teacherService);
        int classroomId = findAvailableClassroom(subjectId, dayNumber, lessonNumber, classroomService);

        return new Lesson(classId, subjectId, teacherId, classroomId, dayNumber, lessonNumber);
    }

    private int findAvailableTeacher(int subjectId, int dayNumber, int lessonNumber, ITeacherService teacherService) {
        ITeachersDAO teachersDAO = new TeacherDAO(MyBatisUtil.getSqlSessionFactory());
        int teacherId = teachersDAO.getTeacherForSubject(subjectId);

        // сheck if new teacher is available
        if (teacherService.isTeacherAvailable(teacherId, dayNumber, lessonNumber)) {
            return teacherId;
        }

        // try to find an alternative
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            int alternativeTeacherId = teachersDAO.getTeacherForSubject(subjectId);
            if (teacherService.isTeacherAvailable(alternativeTeacherId, dayNumber, lessonNumber)) {
                return alternativeTeacherId;
            }
        }

        // if nobody is found return the 1st teacher
        return teacherId;
    }

    private int findAvailableClassroom(int subjectId, int dayNumber, int lessonNumber,
            IClassroomService classroomService) {
        ISubjectService subjectService = new SubjectServiceImpl();

        if (subjectService.requiresSpecialRoom(subjectId)) {
            // for specia; classes return special classroom
            return classroomService.getSpecialClassroomForSubject(subjectId).getId();
        } else {
            // for common classes try to find a classroom
            return findAvailableCommonClassroom(dayNumber, lessonNumber, classroomService);
        }
    }

    private int findAvailableCommonClassroom(int dayNumber, int lessonNumber, IClassroomService classroomService) {
        // take all common classrooms
        List<Integer> commonClassrooms = classroomService.getAllCommonClassrooms()
                .stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());

        // look for the 1st vacant classroom
        for (int classroomId : commonClassrooms) {
            if (classroomService.isClassroomAvailable(classroomId, dayNumber, lessonNumber)) {
                return classroomId;
            }
        }

        // if all are occupied return the 1st
        return commonClassrooms.get(0);
    }
}