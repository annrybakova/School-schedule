package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.model.ClassSubject;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class ClassSubjectDAO implements IClassSubjectDAO {
    private static final Logger log = LogManager.getLogger(ClassSubjectDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.IClassSubjectDAO";
    private final SqlSessionFactory sf;

    public ClassSubjectDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public ClassSubject getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    public List<ClassSubject> getByClassId(int classId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassId", classId);
        }
    }

    public ClassSubject getByClassAndSubject(int classId, int subjectId) {
        Map<String, Object> p = new HashMap<>();
        p.put("classId", classId);
        p.put("subjectId", subjectId);
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByClassAndSubject", p);
        }
    }

    @Override
    public void insert(ClassSubject cs) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", cs);
            log.info("Inserted class_subject: {}", cs);
        }
    }

    @Override
    public void update(ClassSubject cs) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", cs);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }

    @Override
    public List<ClassSubject> getAll() {
        return List.of();
    }

    @Override
    public Integer getRandomSubjectIdForClass(int classId) {
        try (SqlSession s = sf.openSession()){
            return s.selectOne(NS + ".getRandomSubjectIdForClass", classId);
        }
    }

    @Override
    public List<ClassSubject> getBySubjectId(int subjectId) {
        try (SqlSession s = sf.openSession()){
            return s.selectList(NS + ".getBySubjectId", subjectId);
        }
    }

    @Override
    public int getLessonsPerWeek(int classId, int subjectId) {
        Map<String, Object> p = new HashMap<>();
        p.put("classId", classId);
        p.put("subjectId", subjectId);
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getLessonsPerWeek", p);
        }
    }
}
