package com.solvd.school.service.impl;

import com.solvd.school.model.Lesson;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.service.interfaces.ITeacherService;
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

    private final ITeacherService teacherService;
    private final ISubjectService subjectService;
    private final IClassroomService classroomService;
    private final IClassService classService;

    public ValidationServiceImpl(
            ITeacherService teacherService,
            ISubjectService subjectService,
            IClassroomService classroomService,
            IClassService classService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.classService = classService;
    }

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
                validationErrors.add("Gap detected in lessons between "
                        + firstLesson + " and " + lastLesson);
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean validateTeacherConstraints(List<Lesson> lessons) {
        boolean valid = true;

        for (Lesson lesson : lessons) {
            int teacherId = lesson.getTeacher().getId();
            int day = lesson.getDayOfWeek();
            int lessonNum = lesson.getLessonNumber();

            // Rule 1: teacher availability
            if (!teacherService.isTeacherAvailable(teacherId, day, lessonNum)) {
                validationErrors.add("Teacher " + lesson.getTeacher().getLastName() +
                        " is not available on day " + day + " lesson " + lessonNum);
                valid = false;
            }

            // Rule 2: teacher daily load - max 5 lessons
            int lessonsCount = teacherService.getTeacherLessonsCount(teacherId, day);
            if (lessonsCount > 5) {
                validationErrors.add("Teacher " + lesson.getTeacher().getLastName() +
                        " exceeds daily lesson limit (" + lessonsCount + ")");
                valid = false;
            }
        }

        return valid;
    }

    @Override
    public boolean validateClassroomConstraints(List<Lesson> lessons) {
        boolean valid = true;
        // Check classroom capacity, special rooms, etc.
        for (Lesson lesson : lessons) {
            int classId = lesson.getSchoolClass().getId();
            int subjectId = lesson.getSubject().getId();
            int classroomId = lesson.getClassroom().getId();

            int studentCount = classService.getClassById(classId).getStudentCount();
            int roomCapacity = classroomService.getClassroomById(classroomId).getCapacity();

            if (studentCount > roomCapacity) {
                validationErrors.add("Class " + classId + " has " + studentCount +
                        " students, but classroom " + classroomId + " capacity is " + roomCapacity);
                valid = false;
            }
            // Rule 2: Check special room requirement
            if (subjectService.requiresSpecialRoom(subjectId) &&
                    !classroomService.getClassroomById(classroomId).isSpecial()) {
                validationErrors.add("Subject " + subjectService.getSubjectById(subjectId).getName() +
                        " requires a special classroom, but room " + classroomId + " is not special");
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public boolean validateSubjectConstraints(List<Lesson> lessons) {
        boolean valid = true;
        Map<Integer, Integer> lessonsPerDay = new HashMap<>();
        for (Lesson lesson : lessons) {
            int subjectId = lesson.getSubject().getId();
            String subjectName = subjectService.getSubjectById(subjectId).getName();
            int day = lesson.getDayOfWeek();

            // Rule 1: PE not first lesson
            if (subjectName.equalsIgnoreCase("Physical Education") &&
                    lesson.getLessonNumber() == 1) {
                validationErrors.add("Physical Education cannot be scheduled as the first lesson on day " + day);
                valid = false;
            }

            // Rule 2: Max 6 lessons per day
            lessonsPerDay.put(day, lessonsPerDay.getOrDefault(day, 0) + 1);
            if (lessonsPerDay.get(day) > 6) {
                validationErrors.add("Class " + lesson.getSchoolClass().getName() +
                        " exceeds max 6 lessons on day " + day);
                valid = false;
            }
        }

        return valid;
    }

    @Override
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}