package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ITeacherAvailabilityDAO;
import com.solvd.school.model.TeacherAvailability;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class TeacherAvailabilityDAO implements ITeacherAvailabilityDAO {
    private static final Logger log = LogManager.getLogger(TeacherAvailabilityDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.TeacherAvailabilityMapper";
    private final SqlSessionFactory sf;

    public TeacherAvailabilityDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<TeacherAvailability> getByTeacher(int teacherId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByTeacher", teacherId);
        }
    }

    @Override
    public void insert(TeacherAvailability availability) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", availability);
            log.info("Inserted availability: {}", availability);
        }
    }

    @Override
    public void update(TeacherAvailability availability) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", availability);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
