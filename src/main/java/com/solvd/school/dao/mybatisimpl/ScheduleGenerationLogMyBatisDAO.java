package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IScheduleGenerationLogDAO;
import com.solvd.school.model.ScheduleGenerationLog;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

public class ScheduleGenerationLogMyBatisDAO implements IScheduleGenerationLogDAO {
    private static final Logger log = LogManager.getLogger(ScheduleGenerationLogMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.ScheduleGenerationLogMapper";
    private final SqlSessionFactory sf;

    public ScheduleGenerationLogMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public ScheduleGenerationLog getById(int id) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getById", id);
        }
    }

    @Override
    public void insert(ScheduleGenerationLog entity) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", entity);
            log.info("Inserted gen log: {}", entity);
        }
    }

    @Override
    public void update(ScheduleGenerationLog entity) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", entity);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
