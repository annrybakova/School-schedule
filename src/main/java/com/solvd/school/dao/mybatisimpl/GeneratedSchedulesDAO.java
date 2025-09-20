package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IGeneratedSchedulesDAO;
import com.solvd.school.model.GeneratedSchedule;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class GeneratedSchedulesDAO implements IGeneratedSchedulesDAO {
    private static final Logger log = LogManager.getLogger(GeneratedSchedulesDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.GeneratedSchedulesMapper";
    private final SqlSessionFactory sf;

    public GeneratedSchedulesDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<GeneratedSchedule> getByGeneration(int generationId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectList(NS + ".getByGeneration", generationId);
        }
    }

    @Override
    public void insert(GeneratedSchedule gs) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", gs);
            log.info("Inserted generated schedule: {}", gs);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
