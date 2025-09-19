package com.solvd.school.dao.interfaces;

import com.solvd.school.model.SubjectConstraint;

public interface ISubjectConstraintsDAO {
    SubjectConstraint getBySubjectId(int subjectId);

    void insert(SubjectConstraint sc);

    void update(SubjectConstraint sc);

    void deleteBySubjectId(int subjectId);
}
