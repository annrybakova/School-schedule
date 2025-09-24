package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.model.ClassSubject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class ClassSubjectDAO implements IClassSubjectDAO {
    private static final Logger log = LogManager.getLogger(ClassSubjectDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.IClassSubjectDAO";
    private final SqlSessionFactory sf;

    public ClassSubjectDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<ClassSubject> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public Integer getRandomSubjectIdForClass(int classId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getRandomSubjectIdForClass", classId);
        }
    }

    @Override
    public ClassSubject getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public void insert(ClassSubject entity) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", entity);
            log.info("Inserted GA params: {}", entity);
        }
    }

    @Override
    public void update(ClassSubject entity) {
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
}
