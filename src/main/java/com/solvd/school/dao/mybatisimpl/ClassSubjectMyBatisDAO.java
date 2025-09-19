package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassesDAO;
import com.solvd.school.model.ClassSubject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class ClassSubjectMyBatisDAO implements IClassesDAO {
    private static final Logger log = LogManager.getLogger(ClassSubjectMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.ClassesMapper";
    private final SqlSessionFactory sf;

    public ClassSubjectMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public ClassSubject getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public ClassSubject getByName(String name) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByName", name);
        }
    }

    @Override
    public List<ClassSubject> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public void insert(ClassSubject cls) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", cls);
            log.info("Inserted class: {}", cls);
        }
    }

    @Override
    public void update(ClassSubject cls) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", cls);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
