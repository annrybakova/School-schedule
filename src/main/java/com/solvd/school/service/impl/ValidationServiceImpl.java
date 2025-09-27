package com.solvd.school.service.impl;

import com.solvd.school.dao.interfaces.IClassDAO;
import com.solvd.school.dao.interfaces.IClassroomsDAO;
import com.solvd.school.dao.interfaces.ISubjectsDAO;
import com.solvd.school.dao.interfaces.ITeachersDAO;
import com.solvd.school.dao.interfaces.IClassSubjectDAO;
import com.solvd.school.dao.interfaces.ILessonsDAO;
import com.solvd.school.dao.mybatisimpl.ClassDAO;
import com.solvd.school.dao.mybatisimpl.ClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.SubjectDAO;
import com.solvd.school.dao.mybatisimpl.TeacherDAO;
import com.solvd.school.dao.mybatisimpl.ClassSubjectDAO;
import com.solvd.school.dao.mybatisimpl.LessonDAO;
import com.solvd.school.model.Lesson;
import com.solvd.school.model.Teacher;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.service.interfaces.ITeacherService;
import com.solvd.school.service.interfaces.IValidationService;
import com.solvd.school.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class ValidationServiceImpl implements IValidationService {
    private static final Logger logger = LogManager.getLogger(ValidationServiceImpl.class);
    private final List<String> validationErrors = new ArrayList<>();

    private static final ITeacherService teacherService;
    private static final ISubjectService subjectService;
    private static final IClassroomService classroomService;
    private static final IClassService classService;
    private static final ILessonsDAO lessonsDAO;

    static {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();

        ITeachersDAO teachersDAO = new TeacherDAO(sqlSessionFactory);
        ISubjectsDAO subjectsDAO = new SubjectDAO(sqlSessionFactory);
        IClassroomsDAO classroomsDAO = new ClassroomsDAO(sqlSessionFactory);
        IClassDAO classDAO = new ClassDAO(sqlSessionFactory);
        IClassSubjectDAO classSubjectDAO = new ClassSubjectDAO(sqlSessionFactory);
        lessonsDAO = new LessonDAO(sqlSessionFactory);

        teacherService = new TeacherServiceImpl(teachersDAO, lessonsDAO);
        subjectService = new SubjectServiceImpl();
        classroomService = new ClassroomServiceImpl();
        classService = new ClassServiceImpl(classDAO);
    }

    public ValidationServiceImpl() {
    }

    @Override
    public boolean validateSchedule(List<Lesson> lessons) {
        validationErrors.clear();
        return calculateFitness(lessons) >= 100; // If fitness >= 100 - the schedule is valid
    }

    @Override
    public double calculateFitness(List<Lesson> lessons) {
        double fitness = 100.0; // Starting score

        if (lessons == null || lessons.isEmpty()) {
            return 0.0;
        }

        // 1. Penalty for PE in the first lesson: -10 points
        fitness -= calculatePEFirstLessonPenalty(lessons);

        // 2. Penalty for gaps in teachers: -20 points for each teacher
        fitness -= calculateTeacherGapsPenalty(lessons);

        // 3. Penalty for overlapping subjects in classes: -30 points for each overlap
        fitness -= calculateSubjectCollisionPenalty(lessons);

        // 4. Penalty for violating special rooms: -15 points
        fitness -= calculateSpecialRoomPenalty(lessons);

        // 5. Penalty for exceeding the teacher's workload: -25 points
        fitness -= calculateTeacherOverloadPenalty(lessons);

        // 6. Penalty for violation of "10b and 10d without math": -50 points
        fitness -= calculateMathForbiddenClassesPenalty(lessons);

        // 7. Penalty for gaps in class schedules: -10 points for each gap
        fitness -= calculateClassGapsPenalty(lessons);

        // We guarantee that fitness will not be negative
        return Math.max(0.0, fitness);
    }

    // 1. Physical education as the first lesson: -10 points
    private double calculatePEFirstLessonPenalty(List<Lesson> lessons) {
        long peFirstLessonCount = lessons.stream()
                .filter(lesson -> {
                    String subjectName = subjectService.getSubjectById(lesson.getSubjectId()).getName();
                    return subjectName.equalsIgnoreCase("Physical Education") &&
                            lesson.getLessonNumber() == 1;
                })
                .count();

        if (peFirstLessonCount > 0) {
            validationErrors.add("PE as first lesson: -10 points");
            return 10.0 * peFirstLessonCount;
        }
        return 0.0;
    }

    // 2. Teacher gaps: -20 points for each teacher
    private double calculateTeacherGapsPenalty(List<Lesson> lessons) {
        double penalty = 0.0;
        Map<Integer, Map<Integer, List<Integer>>> teacherLessonsByDay = new HashMap<>();

        // We group lessons by teachers and days
        for (Lesson lesson : lessons) {
            teacherLessonsByDay
                    .computeIfAbsent(lesson.getTeacherId(), k -> new HashMap<>())
                    .computeIfAbsent(lesson.getDayOfWeek(), k -> new ArrayList<>())
                    .add(lesson.getLessonNumber());
        }

        // Checking the spaces for each teacher
        for (Map.Entry<Integer, Map<Integer, List<Integer>>> teacherEntry : teacherLessonsByDay.entrySet()) {
            int teacherId = teacherEntry.getKey();

            for (Map.Entry<Integer, List<Integer>> dayEntry : teacherEntry.getValue().entrySet()) {
                List<Integer> lessonNumbers = dayEntry.getValue();
                Collections.sort(lessonNumbers);

                // Checking the sequence of lessons
                for (int i = 1; i < lessonNumbers.size(); i++) {
                    if (lessonNumbers.get(i) - lessonNumbers.get(i - 1) > 1) {
                        penalty += 20.0;
                        validationErrors.add("Teacher " + teacherId + " has gap in schedule: -20 points");
                        break; // One fine per day per teacher
                    }
                }
            }
        }

        return penalty;
    }

    // 3. Overlapping subjects in classes: -30 points for each overlap
    private double calculateSubjectCollisionPenalty(List<Lesson> lessons) {
        double penalty = 0.0;
        Map<String, Integer> subjectTimeSlotCount = new HashMap<>();

        // Count the number of items in each timeslot
        for (Lesson lesson : lessons) {
            String timeSlotKey = lesson.getDayOfWeek() + "-" + lesson.getLessonNumber() + "-" + lesson.getSubjectId();
            subjectTimeSlotCount.put(timeSlotKey, subjectTimeSlotCount.getOrDefault(timeSlotKey, 0) + 1);
        }

        // We penalize for overlaps (more than 1 class has the same subject at the same time)
        for (Map.Entry<String, Integer> entry : subjectTimeSlotCount.entrySet()) {
            if (entry.getValue() > 1) {
                penalty += 30.0 * (entry.getValue() - 1);
                validationErrors.add("Subject collision at time slot " + entry.getKey() + ": -30 points");
            }
        }

        return penalty;
    }

    // 4. Violation of special offices: -15 points
    private double calculateSpecialRoomPenalty(List<Lesson> lessons) {
        double penalty = 0.0;

        for (Lesson lesson : lessons) {
            int subjectId = lesson.getSubjectId();
            int classroomId = lesson.getClassroomId();

            // Сhecking whether the item requires a special cabinet
            if (subjectService.requiresSpecialRoom(subjectId)) {
                if (!classroomService.getClassroomById(classroomId).isSpecial()) {
                    penalty += 15.0;
                    validationErrors.add("Subject " + subjectId + " in non-special room: -15 points");
                }
            }
        }

        return penalty;
    }

    // 5. Excessive teacher workload: -25 points
    private double calculateTeacherOverloadPenalty(List<Lesson> lessons) {
        double penalty = 0.0;
        Map<Integer, Map<Integer, Integer>> teacherDailyLoad = new HashMap<>();

        // We calculate the load
        for (Lesson lesson : lessons) {
            teacherDailyLoad
                    .computeIfAbsent(lesson.getTeacherId(), k -> new HashMap<>())
                    .merge(lesson.getDayOfWeek(), 1, Integer::sum);
        }

        // Checking the excess
        for (Map.Entry<Integer, Map<Integer, Integer>> teacherEntry : teacherDailyLoad.entrySet()) {
            int teacherId = teacherEntry.getKey();
            Teacher teacher = teacherService.getTeacherById(teacherId);

            for (Map.Entry<Integer, Integer> dayEntry : teacherEntry.getValue().entrySet()) {
                int load = dayEntry.getValue();
                if (load > teacher.getMaxLessonsPerDay()) {
                    penalty += 25.0;
                    validationErrors.add("Teacher " + teacherId + " overloaded: -25 points");
                }
            }
        }

        return penalty;
    }

    // 6. Violation of "10b and 10d without mathematics": -50 points
    private double calculateMathForbiddenClassesPenalty(List<Lesson> lessons) {
        double penalty = 0.0;

        for (Lesson lesson : lessons) {
            int classId = lesson.getClassId();
            int subjectId = lesson.getSubjectId();

            // 10b (2) and 10d (4) must not have mathematics (1)
            if ((classId == 2 || classId == 4) && subjectId == 1) {
                penalty += 50.0;
                validationErrors.add("Class " + getClassName(classId) + " has forbidden Math: -50 points");
            }
        }

        return penalty;
    }

    // 7. Windows in class schedules: -10 points for each window
    private double calculateClassGapsPenalty(List<Lesson> lessons) {
        double penalty = 0.0;
        Map<Integer, Map<Integer, List<Integer>>> classLessonsByDay = new HashMap<>();

        // We group lessons by class and day.
        for (Lesson lesson : lessons) {
            classLessonsByDay
                    .computeIfAbsent(lesson.getClassId(), k -> new HashMap<>())
                    .computeIfAbsent(lesson.getDayOfWeek(), k -> new ArrayList<>())
                    .add(lesson.getLessonNumber());
        }

        // We check the windows for each class
        for (Map.Entry<Integer, Map<Integer, List<Integer>>> classEntry : classLessonsByDay.entrySet()) {
            int classId = classEntry.getKey();

            for (Map.Entry<Integer, List<Integer>> dayEntry : classEntry.getValue().entrySet()) {
                List<Integer> lessonNumbers = dayEntry.getValue();
                Collections.sort(lessonNumbers);

                // Checking the sequence of lessons
                for (int i = 1; i < lessonNumbers.size(); i++) {
                    if (lessonNumbers.get(i) - lessonNumbers.get(i - 1) > 1) {
                        penalty += 10.0;
                        validationErrors.add("Class " + classId + " has gap in schedule: -10 points");
                        break; // One fine per day per class
                    }
                }
            }
        }

        return penalty;
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