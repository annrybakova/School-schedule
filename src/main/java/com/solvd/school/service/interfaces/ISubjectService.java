package com.solvd.school.service.interfaces;

import com.solvd.school.model.Subject;
import java.util.List;

public interface ISubjectService {
    Subject getSubjectById(int id);

    List<Subject> getAllSubjects();

    List<Subject> getSubjectsForClass(int classId);

    int getLessonsPerWeekForClass(int classId, int subjectId);

    boolean requiresSpecialRoom(int subjectId);

    Integer getRandomSubjectIdForClass(int classId);
}