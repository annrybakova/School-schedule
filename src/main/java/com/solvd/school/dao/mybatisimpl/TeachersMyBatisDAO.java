package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.model.Teacher;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

import java.util.List;

public class TeachersMyBatisDAO implements ITeachersDAO {
    private static final Logger log = LogManager.getLogger(TeachersMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.TeachersMapper";
    private final SqlSessionFactory sf;

    public TeachersMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Teacher getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<Teacher> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public List<Teacher> getBySubject(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getBySubject", subjectId);
        }
    }

    @Override
    public void insert(Teacher teacher) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", teacher);
            log.info("Inserted teacher: {}", teacher);
        }
    }

    @Override
    public void update(Teacher teacher) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", teacher);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
