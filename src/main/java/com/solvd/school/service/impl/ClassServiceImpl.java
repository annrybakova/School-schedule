package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassDAO;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.service.interfaces.IClassService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClassServiceImpl implements IClassService {
    private static final Logger logger = LogManager.getLogger(ClassServiceImpl.class);
    private final IClassDAO classDAO;

    public ClassServiceImpl(IClassDAO classDAO) {
        this.classDAO = classDAO;
    }

    @Override
    public List<SchoolClass> getAllClasses() {
        return classDAO.getAll();
    }

    @Override
    public SchoolClass getClassById(int id) {
        return classDAO.getById(id);
    }
}
