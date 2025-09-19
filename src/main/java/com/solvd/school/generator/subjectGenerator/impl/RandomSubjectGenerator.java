package com.solvd.school.generator.subjectGenerator.impl;

import com.solvd.school.dao.mybatisimpl.SubjectsMyBatisDAO;
import com.solvd.school.generator.subjectGenerator.interfaces.ISubjectGenerator;
import com.solvd.school.model.Subject;
import com.solvd.school.util.MyBatisUtil;
import com.solvd.school.util.Randomizer;

import java.util.List;

public class RandomSubjectGenerator implements ISubjectGenerator {

    private static final List<Subject> subjects;

    static {
        SubjectsMyBatisDAO subjectsDAO = new SubjectsMyBatisDAO(MyBatisUtil.getSqlSessionFactory());
        subjects = subjectsDAO.getAll();
    }

    @Override
    public Subject getSubject() {
        int maxSubjectsIndex = subjects.size() - 1;
        int randomSubjectsIndex = Randomizer.randomNum(0, maxSubjectsIndex);

        return subjects.get(randomSubjectsIndex);
    }
}
