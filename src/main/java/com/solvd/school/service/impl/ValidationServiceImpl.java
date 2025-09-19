package com.solvd.school.service.impl;

import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.IValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class ValidationServiceImpl implements IValidationService {
    private static final Logger logger = LogManager.getLogger(ValidationServiceImpl.class);
    private final List<String> validationErrors = new ArrayList<>();

    @Override
    public boolean validateSchedule(List<Lesson> lessons) {
        validationErrors.clear();

        boolean valid = validateNoGaps(lessons) &&
                validateTeacherConstraints(lessons) &&
                validateClassroomConstraints(lessons) &&
                validateSubjectConstraints(lessons);

        if (!valid) {
            logger.warn("Schedule validation failed with errors: {}", validationErrors);
        }

        return valid;
    }

    @Override
    public boolean validateNoGaps(List<Lesson> lessons) {
        // Implementation for checking no gaps in schedule
        return true; // Placeholder
    }

    @Override
    public boolean validateTeacherConstraints(List<Lesson> lessons) {
        // Check teacher max lessons per day, availability, etc.
        return true; // Placeholder
    }

    @Override
    public boolean validateClassroomConstraints(List<Lesson> lessons) {
        // Check classroom capacity, special rooms, etc.
        return true; // Placeholder
    }

    @Override
    public boolean validateSubjectConstraints(List<Lesson> lessons) {
        // Check subject constraints (PE not first lesson, etc.)
        return true; // Placeholder
    }

    @Override
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}