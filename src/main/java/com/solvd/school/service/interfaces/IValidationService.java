package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import java.util.List;

public interface IValidationService {
    boolean validateSchedule(List<Lesson> lessons);
    double calculateFitness(List<Lesson> lessons); // НОВИЙ МЕТОД
    List<String> getValidationErrors();

    boolean validateNoGaps(List<Lesson> lessons);
    boolean validateTeacherConstraints(List<Lesson> lessons);
    boolean validateClassroomConstraints(List<Lesson> lessons);
    boolean validateSubjectConstraints(List<Lesson> lessons);
}