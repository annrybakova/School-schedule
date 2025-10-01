package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassDAO;
import com.solvd.school.dao.mybatisimpl.ClassDAO;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ClassServiceImpl implements IClassService {
    private static final IClassDAO classDAO;

    static {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        classDAO = new ClassDAO(sqlSessionFactory);
    }

    public ClassServiceImpl() {}

    @Override
    public List<SchoolClass> getAllClasses() {
        return classDAO.getAll();
    }

    @Override
    public SchoolClass getClassById(int id) {
        return classDAO.getById(id);
    }
}
