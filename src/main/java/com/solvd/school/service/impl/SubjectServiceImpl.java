package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ISubjectsDAO;
import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.SubjectDAO;
import com.solvd.school.model.ClassSubject;
import com.solvd.school.model.Subject;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectServiceImpl implements ISubjectService {
    private static final Logger logger = LogManager.getLogger(SubjectServiceImpl.class);
    private static final ISubjectsDAO subjectsDAO;
    private static final IClassSubjectDAO classSubjectDAO;

    static {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        subjectsDAO = new SubjectDAO(sqlSessionFactory);
        classSubjectDAO = new ClassSubjectDAO(sqlSessionFactory);
    }

    public SubjectServiceImpl() {}

    @Override
    public Subject getSubjectById(int id) {
        logger.debug("Getting subject by id: {}", id);
        return subjectsDAO.getById(id);
    }

    @Override
    public List<Subject> getAllSubjects() {
        logger.debug("Getting all subjects");
        return subjectsDAO.getAll();
    }

    @Override
    public List<Subject> getSubjectsForClass(int classId) {
        logger.debug("Getting subjects for class id: {}", classId);
        List<ClassSubject> classSubjects = classSubjectDAO.getByClassId(classId);
        return classSubjects.stream()
                .map(classSubject -> subjectsDAO.getById(classSubject.getSubjectId()))
                .collect(Collectors.toList());
    }

    @Override
    public int getLessonsPerWeekForClass(int classId, int subjectId) {
        logger.debug("Getting lessons per week for class {} and subject {}", classId, subjectId);
        ClassSubject classSubject = classSubjectDAO.getByClassAndSubject(classId, subjectId);
        return classSubject != null ? classSubject.getLessonsPerWeek() : 0;
    }

    @Override
    public boolean requiresSpecialRoom(int subjectId) {
        logger.debug("Checking if subject {} requires special room", subjectId);
        Subject subject = subjectsDAO.getById(subjectId);
        return subject != null && subject.isRequiresSpecialRoom();
    }

    @Override
    public Integer getRandomSubjectIdForClass(int classId) {
        logger.debug("Getting random subject id for class {}", classId);
        return classSubjectDAO.getRandomSubjectIdForClass(classId);
    }
}