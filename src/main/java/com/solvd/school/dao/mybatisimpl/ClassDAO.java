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
    private static final String NS = "com.solvd.school.dao.interfaces.IClassDAO";
    private final SqlSessionFactory sf;

    public ClassDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public SchoolClass getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public void insert(SchoolClass entity) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", entity);
        }
    }

    @Override
    public void update(SchoolClass entity) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", entity);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }

    @Override
    public List<SchoolClass> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }
}
