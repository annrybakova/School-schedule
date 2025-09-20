package com.solvd.school.generator.lessonGenerator.impl;

import com.solvd.school.dao.interfaces.IClassSubjectsDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.generator.lessonGenerator.interfaces.ILessonGenerator;
import com.solvd.school.model.ClassSubject;
import com.solvd.school.model.Lesson;
import com.solvd.school.util.MyBatisUtil;

import java.util.ArrayList;
import java.util.List;

public class RandomLessonGenerator implements ILessonGenerator {
    @Override
    public Lesson getLesson() {
        IClassSubjectsDAO classSubjectDAO = new ClassSubjectDAO(MyBatisUtil.getSqlSessionFactory());

        List<ClassSubject> classSubjects = classSubjectDAO.getAll();

        List<Integer> allSubjectsIdForClass = new ArrayList<>();
        for (ClassSubject classSubject : classSubjects) {
            if (classSubject.getClassId() == 1) {
                allSubjectsIdForClass.add(classSubject.getSubjectId());
            }
        }

        return null;
    }

    private List<Integer> getAllSubjectsIdForClass(int classId) {

    }
}
