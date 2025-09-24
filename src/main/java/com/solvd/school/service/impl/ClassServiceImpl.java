package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassDAO;
import com.solvd.school.dao.mybatisimpl.ClassDAO;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClassServiceImpl implements IClassService {
    private static final Logger logger = LogManager.getLogger(ClassServiceImpl.class);
    private static final IClassDAO classDAO = new ClassDAO(MyBatisUtil.getSqlSessionFactory());

    @Override
    public List<SchoolClass> getAllClasses() {
        return classDAO.getAll();
    }
}
