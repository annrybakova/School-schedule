package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.ISubjectsDAO;
import com.solvd.school.model.Subject;
import com.solvd.school.service.interfaces.ISubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SubjectServiceImpl implements ISubjectService {
    private static final Logger logger = LogManager.getLogger(SubjectServiceImpl.class);
    private final ISubjectsDAO subjectsDAO;

    public SubjectServiceImpl(ISubjectsDAO subjectsDAO) {
        this.subjectsDAO = subjectsDAO;
    }

    @Override
    public Subject getSubjectById(int id) {
        return subjectsDAO.getById(id);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectsDAO.getAll();
    }

    @Override
    public List<Subject> getSubjectsForClass(int classId) {
        return subjectsDAO.getByClassId(classId);
    }

    @Override
    public int getLessonsPerWeekForClass(int classId, int subjectId) {
        return subjectsDAO.getLessonsPerWeek(classId, subjectId);
    }

    @Override
    public boolean requiresSpecialRoom(int subjectId) {
        Subject subject = subjectsDAO.getById(subjectId);
        return subject != null && subject.isRequiresSpecialRoom();
    }
}