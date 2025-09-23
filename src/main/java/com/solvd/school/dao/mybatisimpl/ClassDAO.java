package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassDAO;
import com.solvd.school.model.SchoolClass;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClassDAO implements IClassDAO {
    private static final Logger log = LogManager.getLogger(ClassDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.class-mapper";
    private final SqlSessionFactory sf;

    public ClassDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public SchoolClass getById(int id) {
        return null;
    }

    @Override
    public void insert(SchoolClass entity) {

    }

    @Override
    public void update(SchoolClass entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<SchoolClass> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }
}
