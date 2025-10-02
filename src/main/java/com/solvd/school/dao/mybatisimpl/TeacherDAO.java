package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.model.Teacher;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

import java.util.List;

public class TeacherDAO implements ITeachersDAO {
    private static final Logger log = LogManager.getLogger(TeacherDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.ITeachersDAO";
    private final SqlSessionFactory sf;

    public TeacherDAO(SqlSessionFactory sf) {
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
    public Integer getTeacherForSubject(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getTeacherForSubject", subjectId);
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

    @Override
    public Teacher getByFirstName(String firstName) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByFirstName", firstName);
        }
    }

    @Override
    public Teacher getByLastName(String lastName) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByLastName", lastName);
        }
    }
}
