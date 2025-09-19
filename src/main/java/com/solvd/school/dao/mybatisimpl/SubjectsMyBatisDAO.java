package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ISubjectsDAO;
import com.solvd.school.model.Subject;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

import java.util.List;

public class SubjectsMyBatisDAO implements ISubjectsDAO {
    private static final Logger log = LogManager.getLogger(SubjectsMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.SubjectsMapper";
    private final SqlSessionFactory sf;

    public SubjectsMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Subject getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<Subject> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public void insert(Subject subject) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", subject);
            log.info("Inserted subject: {}", subject);
        }
    }

    @Override
    public void update(Subject subject) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", subject);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
