package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import com.solvd.school.model.schedule.SchoolClassesSchedule;

import java.util.List;

public interface IValidationService {
    boolean validateSchedule(List<Lesson> lessons);
    int fitness(SchoolClassesSchedule schedule);

    boolean validateClassSubjectRules(List<Lesson> lessons);

    boolean validateSpecialRoomConstraints(List<Lesson> lessons);

    List<String> getValidationErrors();

    boolean validateNoGaps(List<Lesson> lessons);
    boolean validateTeacherConstraints(List<Lesson> lessons);
    boolean validateClassroomConstraints(List<Lesson> lessons);
    boolean validateSubjectConstraints(List<Lesson> lessons);
}