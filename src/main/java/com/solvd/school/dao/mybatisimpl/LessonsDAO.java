package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.model.Lesson;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class LessonsDAO implements ILessonsDAO {
    private static final Logger log = LogManager.getLogger(LessonsDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.LessonsMapper";
    private final SqlSessionFactory sf;

    public LessonsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Lesson getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<Lesson> getByClassAndDay(int classId, int dayOfWeek) {
        Map<String, Object> p = new HashMap<>();
        p.put("classId", classId);
        p.put("dayOfWeek", dayOfWeek);
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassAndDay", p);
        }
    }

    @Override
    public void insert(Lesson lesson) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", lesson);
            log.info("Inserted lesson: {}", lesson);
        }
    }

    @Override
    public void update(Lesson lesson) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", lesson);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
