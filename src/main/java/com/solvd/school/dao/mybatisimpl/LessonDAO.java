package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.util.MyBatisUtil;

import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class LessonDAO implements ILessonsDAO {
    private static final Logger log = LogManager.getLogger(LessonDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.ILessonsDAO";
    private final SqlSessionFactory sf;

    public LessonDAO(SqlSessionFactory sf) {
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
    public int countAll(Lesson lesson) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".countAll", lesson);
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

    @Override
    public List<Lesson> getByClass(int classId) {
        try (SqlSession s = sf.openSession(true)) {
            return s.selectList(NS + ".getByClass", classId);
        }
    }

    @Override
    public List<Lesson> getByClassroomAndDay(int classroomId, int dayOfWeek) {
        Map<String, Object> p = new HashMap<>();
        p.put("classroomId", classroomId);
        p.put("dayOfWeek", dayOfWeek);
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassroomAndDay", p);
        }
    }

    @Override
    public List<Lesson> getByTeacher(int teacherId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByTeacher", teacherId);
        }
    }

    @Override
    public List<Lesson> getByClassroom(int classroomId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByClassroom", classroomId);
        }
    }

    @Override
    public List<Lesson> getByTeacherAndDay(int teacherId, int dayOfWeek) {
        Map<String, Object> p = new HashMap<>();
        p.put("teacherId", teacherId);
        p.put("dayOfWeek", dayOfWeek);
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByTeacherAndDay", p);
        }
    }

    @Override
    public Lesson getByTeacherAndTime(int teacherId, int dayNumber, int lessonNumber) {
        Map<String, Object> p = new HashMap<>();
        p.put("teacherId", teacherId);
        p.put("dayNumber", dayNumber);
        p.put("lessonNumber", lessonNumber);

        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByTeacherAndTime", p);
        }
    }

    @Override
    public Lesson getByClassroomAndTime(int classroomId, int dayNumber, int lessonNumber) {
        Map<String, Object> p = new HashMap<>();
        p.put("classroomId", classroomId);
        p.put("dayNumber", dayNumber);
        p.put("lessonNumber", lessonNumber);

        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getByClassroomAndTime", p);
        }
    }

}
