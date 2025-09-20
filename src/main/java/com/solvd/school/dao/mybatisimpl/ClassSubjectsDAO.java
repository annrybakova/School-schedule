package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IClassSubjectsDAO;
import com.solvd.school.model.ClassSubject;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class ClassSubjectsDAO implements IClassSubjectsDAO {
    private static final Logger log = LogManager.getLogger(ClassSubjectsDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.ClassSubjectsMapper";
    private final SqlSessionFactory sf;

    public ClassSubjectsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public ClassSubject getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<ClassSubject> getByClassId(int classId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassId", classId);
        }
    }

    @Override
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
}
