package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IStudentsDAO;
import com.solvd.school.model.Student;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class StudentsMyBatisDAO implements IStudentsDAO {
    private static final Logger log = LogManager.getLogger(StudentsMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.StudentsMapper";
    private final SqlSessionFactory sf;

    public StudentsMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Student getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<Student> getByClassId(int classId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassId", classId);
        }
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByGroupId", groupId);
        }
    }

    @Override
    public List<Student> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public void insert(Student student) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", student);
            log.info("Inserted student: {}", student);
        }
    }

    @Override
    public void update(Student student) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", student);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
