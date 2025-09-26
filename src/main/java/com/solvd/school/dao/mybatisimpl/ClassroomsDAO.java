package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassroomsDAO;
import com.solvd.school.model.Classroom;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

import java.util.List;

public class ClassroomsDAO implements IClassroomsDAO {
    private static final Logger log = LogManager.getLogger(ClassroomsDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.IClassroomsDAO";
    private final SqlSessionFactory sf;

    public ClassroomsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Classroom getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<Classroom> getAll() {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getAll");
        }
    }

    @Override
    public Integer getRandomClassroomId(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getRandomClassroomId", subjectId);
        }
    }

    @Override
    public Classroom getCommonClassroom(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getCommonClassroom", subjectId);
        }
    }

    @Override
    public void insert(Classroom classroom) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", classroom);
            log.info("Inserted classroom: {}", classroom);
        }
    }

    @Override
    public void update(Classroom classroom) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", classroom);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
