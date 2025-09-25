package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import java.util.List;

public interface IValidationService {
    boolean validateSchedule(List<Lesson> lessons);

    boolean validateNoGaps(List<Lesson> lessons);

    boolean validateTeacherConstraints(List<Lesson> lessons);

    boolean validateClassroomConstraints(List<Lesson> lessons);

    boolean validateSubjectConstraints(List<Lesson> lessons);

    List<String> getValidationErrors();
}