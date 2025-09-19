package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ISubjectConstraintsDAO;
import com.solvd.school.model.SubjectConstraint;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

public class SubjectConstraintsMyBatisDAO implements ISubjectConstraintsDAO {
    private static final Logger log = LogManager.getLogger(SubjectConstraintsMyBatisDAO.class);
    private static final String NS = "com.solvd.school.mybatis.mappers.SubjectConstraintsMapper";
    private final SqlSessionFactory sf;

    public SubjectConstraintsMyBatisDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public SubjectConstraint getBySubjectId(int subjectId) {
        try (SqlSession s = sf.openSession()) {
            return s.selectOne(NS + ".getBySubjectId", subjectId);
        }
    }

    @Override
    public void insert(SubjectConstraint sc) {
        try (SqlSession s = sf.openSession(true)) {
            s.insert(NS + ".insert", sc);
            log.info("Inserted subject_constraints: {}", sc);
        }
    }

    @Override
    public void update(SubjectConstraint sc) {
        try (SqlSession s = sf.openSession(true)) {
            s.update(NS + ".update", sc);
        }
    }

    @Override
    public void deleteBySubjectId(int subjectId) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".deleteBySubjectId", subjectId);
        }
    }
}
