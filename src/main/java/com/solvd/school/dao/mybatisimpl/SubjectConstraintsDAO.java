package com.solvd.school.dao.mybatisimpl;

import com.solvd.school.dao.interfaces.ISubjectConstraintsDAO;
import com.solvd.school.model.SubjectConstraint;
import org.apache.ibatis.session.*;
import org.apache.logging.log4j.*;

public class SubjectConstraintsDAO implements ISubjectConstraintsDAO {
    private static final Logger log = LogManager.getLogger(SubjectConstraintsDAO.class);
    private static final String NS = "com.solvd.school.dao.interfaces.ISubjectConstraintsDAO";
    private final SqlSessionFactory sf;

    public SubjectConstraintsDAO(SqlSessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public SubjectConstraint getById(int subjectId) {
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
    public void delete(int subjectId) {
        try (SqlSession s = sf.openSession(true)) {
            s.delete(NS + ".deleteBySubjectId", subjectId);
        }
    }
}
