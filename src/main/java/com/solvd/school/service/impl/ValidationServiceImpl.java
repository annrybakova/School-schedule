package com.solvd.school.service.impl;

import com.solvd.school.model.Lesson;
import com.solvd.school.model.Teacher;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.service.interfaces.ITeacherService;
import com.solvd.school.service.interfaces.IValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
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
                validateSubjectConstraints(lessons) &&
                validateClassSubjectRules(lessons) &&
                validateSpecialRoomConstraints(lessons);

        if (!valid) {
            logger.warn("Schedule validation failed with errors: {}", validationErrors);
        }

        return valid;
    }

    // Сhecking rules for classes and subjects
    private boolean validateClassSubjectRules(List<Lesson> lessons) {
        boolean valid = true;

        for (Lesson lesson : lessons) {
            int classId = lesson.getClassId();
            int subjectId = lesson.getSubjectId();
            String className = getClassName(classId);

            // 10b (id=2) and 10d (id=4) should not have Mathematics (id=1)
            if ((classId == 2 || classId == 4) && subjectId == 1) {
                validationErrors.add("Class " + className + " should not have Mathematics");
                valid = false;
            }
        }
        return valid;
    }

    // Сheck the limit of 1 room for physics/chemistry
    private boolean validateSpecialRoomConstraints(List<Lesson> lessons) {
        boolean valid = true;
        Map<Integer, Map<Integer, Set<Integer>>> subjectRoomsPerDay = new HashMap<>();

        for (Lesson lesson : lessons) {
            int subjectId = lesson.getSubjectId();
            int dayOfWeek = lesson.getDayOfWeek();
            int classroomId = lesson.getClassroomId();

            // Сheck only physics (id=2) and chemistry (id=3)
            if (subjectId == 2 || subjectId == 3) {
                subjectRoomsPerDay
                        .computeIfAbsent(subjectId, k -> new HashMap<>())
                        .computeIfAbsent(dayOfWeek, k -> new HashSet<>())
                        .add(classroomId);
            }
        }

        // Checking no more than 1 office per day for special items
        for (Map.Entry<Integer, Map<Integer, Set<Integer>>> subjectEntry : subjectRoomsPerDay.entrySet()) {
            int subjectId = subjectEntry.getKey();
            String subjectName = subjectService.getSubjectById(subjectId).getName();

            for (Map.Entry<Integer, Set<Integer>> dayEntry : subjectEntry.getValue().entrySet()) {
                int day = dayEntry.getKey();
                int roomCount = dayEntry.getValue().size();

                if (roomCount > 1) {
                    validationErrors.add(subjectName + " should use only 1 classroom per day, but uses " + roomCount + " on day " + day);
                    valid = false;
                }
            }
        }

        return valid;
    }

    // Helper method for getting the class name
    private String getClassName(int classId) {
        return switch (classId) {
            case 1 -> "10a";
            case 2 -> "10b";
            case 3 -> "10c";
            case 4 -> "10d";
            default -> "Unknown";
        };
    }

    @Override
    public boolean validateNoGaps(List<Lesson> lessons) {
        if (lessons == null || lessons.isEmpty()) {
            return true;
        }

        Map<Integer, List<Lesson>> lessonsByClass = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getClassId));

        for (List<Lesson> classLessons : lessonsByClass.values()) {
            Map<Integer, List<Lesson>> lessonsByDay = classLessons.stream()
                    .collect(Collectors.groupingBy(Lesson::getDayOfWeek));

            for (List<Lesson> dayLessons : lessonsByDay.values()) {
                dayLessons.sort(Comparator.comparingInt(Lesson::getLessonNumber));

                Set<Integer> scheduledNumbers = dayLessons.stream()
                        .map(Lesson::getLessonNumber)
                        .collect(Collectors.toSet());

                int first = dayLessons.get(0).getLessonNumber();
                int last = dayLessons.get(dayLessons.size() - 1).getLessonNumber();

                for (int i = first; i <= last; i++) {
                    if (!scheduledNumbers.contains(i)) {
                        validationErrors.add("Gap detected in lessons between " + first + " and " + last);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean validateTeacherConstraints(List<Lesson> lessons) {
        boolean valid = true;
        Map<Integer, Map<Integer, Integer>> teacherDailyLoad = new HashMap<>();

        for (Lesson lesson : lessons) {
            int teacherId = lesson.getTeacherId();
            int day = lesson.getDayOfWeek();
            int lessonNum = lesson.getLessonNumber();

            // Rule 1: teacher availability
            if (!teacherService.isTeacherAvailable(teacherId, day, lessonNum)) {
                validationErrors.add("Teacher " + teacherId + " is not available on day " + day + " lesson " + lessonNum);
                valid = false;
            }

            // Rule 2: teacher daily load
            teacherDailyLoad
                    .computeIfAbsent(teacherId, k -> new HashMap<>())
                    .merge(day, 1, Integer::sum);
        }

        // Load check after calculation
        for (Map.Entry<Integer, Map<Integer, Integer>> teacherEntry : teacherDailyLoad.entrySet()) {
            int teacherId = teacherEntry.getKey();
            Teacher teacher = teacherService.getTeacherById(teacherId);

            for (Map.Entry<Integer, Integer> dayEntry : teacherEntry.getValue().entrySet()) {
                int day = dayEntry.getKey();
                int load = dayEntry.getValue();

                if (load > teacher.getMaxLessonsPerDay()) {
                    validationErrors.add("Teacher " + teacher.getLastName() +
                            " exceeds daily limit (" + load + " > " + teacher.getMaxLessonsPerDay() + ") on day " + day);
                    valid = false;
                }
            }
        }

        return valid;
    }

    @Override
    public boolean validateClassroomConstraints(List<Lesson> lessons) {
        boolean valid = true;

        for (Lesson lesson : lessons) {
            int classId = lesson.getClassId();
            int subjectId = lesson.getSubjectId();
            int classroomId = lesson.getClassroomId();

            // Rule 1: classroom availability
            if (!classroomService.isClassroomAvailable(classroomId, lesson.getDayOfWeek(), lesson.getLessonNumber())) {
                validationErrors.add("Classroom " + classroomId + " is not available at this time");
                valid = false;
            }

            // Rule 2: capacity check
            int studentCount = classService.getClassById(classId).getStudentCount();
            if (!classroomService.hasEnoughCapacity(classroomId, studentCount)) {
                validationErrors.add("Classroom " + classroomId + " capacity exceeded for class " + classId);
                valid = false;
            }

            // Rule 3: special room requirement
            if (subjectService.requiresSpecialRoom(subjectId) &&
                    !classroomService.getClassroomById(classroomId).isSpecial()) {
                validationErrors.add("Subject requires special classroom, but room " + classroomId + " is not special");
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public boolean validateSubjectConstraints(List<Lesson> lessons) {
        boolean valid = true;
        Map<Integer, Map<Integer, Integer>> classDailyLessons = new HashMap<>();

        for (Lesson lesson : lessons) {
            int subjectId = lesson.getSubjectId();
            int classId = lesson.getClassId();
            int day = lesson.getDayOfWeek();
            String subjectName = subjectService.getSubjectById(subjectId).getName();

            // Rule 1: PE not first lesson
            if (subjectName.equalsIgnoreCase("Physical Education") && lesson.getLessonNumber() == 1) {
                validationErrors.add("PE cannot be first lesson for class " + classId + " on day " + day);
                valid = false;
            }

            // Rule 2: Max 6 lessons per day per class
            classDailyLessons
                    .computeIfAbsent(classId, k -> new HashMap<>())
                    .merge(day, 1, Integer::sum);
        }

        // Checking the maximum number of lessons
        for (Map.Entry<Integer, Map<Integer, Integer>> classEntry : classDailyLessons.entrySet()) {
            int classId = classEntry.getKey();

            for (Map.Entry<Integer, Integer> dayEntry : classEntry.getValue().entrySet()) {
                int day = dayEntry.getKey();
                int lessonCount = dayEntry.getValue();

                if (lessonCount > 6) {
                    validationErrors.add("Class " + classId + " exceeds 6 lessons limit on day " + day + " (" + lessonCount + " lessons)");
                    valid = false;
                }
            }
        }

        return valid;
    }

    @Override
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}