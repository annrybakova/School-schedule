package com.solvd.school.service.impl;

import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.IValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (lessons == null || lessons.isEmpty()) {
            return true;
        }

        lessons.sort(Comparator.comparingInt(Lesson::getLessonNumber));

        int firstLesson = lessons.get(0).getLessonNumber();
        int lastLesson = lessons.get(lessons.size() - 1).getLessonNumber();

        Set<Integer> scheduledNumbers = lessons.stream()
                .map(Lesson::getLessonNumber)
                .collect(Collectors.toSet());

        for (int i = firstLesson; i <= lastLesson; i++) {
            if (!scheduledNumbers.contains(i)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean validateTeacherConstraints(List<Lesson> lessons) {
        // Check teacher max lessons per day, availability, etc.

        return true; // Placeholder

    }

    @Override
    public boolean validateClassroomConstraints(List<Lesson> lessons) {
        // Check classroom capacity, special rooms, etc.
        for (Lesson lesson : lessons) {
            int studentCount = lesson.getSchoolClass().getStudentCount();
            int roomCapacity = lesson.getClassroom().getCapacity();

            if (studentCount > roomCapacity) {
                System.out.printf("Class %s has %d students, but classroom %s can only hold %d.%n",
                        lesson.getSchoolClass().getName(), studentCount,
                        lesson.getClassroom().getRoomNumber(), roomCapacity);
                return false;
            }
            // Rule 2: Check special room requirement
            if (lesson.getSubject().isRequiresSpecialRoom()) {
                if (!lesson.getClassroom().isSpecial()) {
                    return false;
                }
            }
        }
        return true; // Placeholder
    }

    @Override
    public boolean validateSubjectConstraints(List<Lesson> lessons) {
        Map<Integer, Integer> lessonsPerDay = new HashMap<>();
        for (Lesson lesson : lessons) {
            String subjectName = lesson.getSubject().getName();

            // Rule 1: PE not first lesson
            if (subjectName.equalsIgnoreCase("Physical Education") &&
                    lesson.getLessonNumber() == 1) {
                return false;
            }
            int day = lesson.getDayOfWeek();
            lessonsPerDay.put(day, lessonsPerDay.getOrDefault(day, 0) + 1);

            // Rule 2: If we exceed 6 lessons for any day — fail immediately
            if (lessonsPerDay.get(day) > 6) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}