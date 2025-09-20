package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ISpecialClassroomsDAO;
import com.solvd.school.model.SpecialClassroom;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class SpecialClassroomsDAO implements ISpecialClassroomsDAO {
    private static final Logger log = LogManager.getLogger(SpecialClassroomsDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.SpecialClassroomsMapper";
    private final SqlSessionFactory sf;

    public SpecialClassroomsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public SpecialClassroom getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public List<SpecialClassroom> getBySubjectId(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getBySubjectId", subjectId);
        }
    }

    @Override
    public void insert(SpecialClassroom sc) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", sc);
            log.info("Inserted special_classroom: {}", sc);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
