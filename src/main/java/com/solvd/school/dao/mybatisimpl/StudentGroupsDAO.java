package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IStudentGroupsDAO;
import com.solvd.school.model.StudentGroup;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class StudentGroupsDAO implements IStudentGroupsDAO {
    private static final Logger log = LogManager.getLogger(StudentGroupsDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.StudentGroupsMapper";
    private final SqlSessionFactory sf;

    public StudentGroupsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public StudentGroup getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<StudentGroup> getByClassId(int classId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassId", classId);
        }
    }

    @Override
    public List<StudentGroup> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public void insert(StudentGroup group) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", group);
            log.info("Inserted student group: {}", group);
        }
    }

    @Override
    public void update(StudentGroup group) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", group);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
