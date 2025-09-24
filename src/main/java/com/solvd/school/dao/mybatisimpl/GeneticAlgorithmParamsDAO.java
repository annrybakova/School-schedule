package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.IGeneticAlgorithmParamsDAO;
import com.solvd.school.model.GeneticAlgorithmParams;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

public class GeneticAlgorithmParamsDAO implements IGeneticAlgorithmParamsDAO {
    private static final Logger log = LogManager.getLogger(GeneticAlgorithmParamsDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.IGeneticAlgorithmParamsDAO";
    private final SqlSessionFactory sf;

    public GeneticAlgorithmParamsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public GeneticAlgorithmParams getActive() {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getActive");
        }
    }

    @Override
    public void insert(GeneticAlgorithmParams params) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", params);
            log.info("Inserted GA params: {}", params);
        }
    }

    @Override
    public void update(GeneticAlgorithmParams params) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", params);
        }
    }

    @Override
    public void delete(int id) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".delete", id);
        }
    }
}
