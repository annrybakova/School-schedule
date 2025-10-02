package com.solvd.school;

import com.solvd.school.model.Lesson;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.DailySchedule;
import com.solvd.school.model.schedule.SchoolClassesSchedule;
import com.solvd.school.model.schedule.WeeklySchedule;
import com.solvd.school.scheduler.impl.GeneticScheduler;
import com.solvd.school.scheduler.interfaces.IScheduler;
import com.solvd.school.service.impl.ClassServiceImpl;
import com.solvd.school.service.impl.ClassroomServiceImpl;
import com.solvd.school.service.impl.LessonServiceImpl;
import com.solvd.school.service.impl.SubjectServiceImpl;
import com.solvd.school.service.impl.TeacherServiceImpl;
import com.solvd.school.service.impl.ValidationServiceImpl;
import com.solvd.school.service.interfaces.IClassService;
import com.solvd.school.service.interfaces.IClassroomService;
import com.solvd.school.service.interfaces.ILessonService;
import com.solvd.school.service.interfaces.ISubjectService;
import com.solvd.school.service.interfaces.ITeacherService;
import com.solvd.school.service.interfaces.IValidationService;
import com.solvd.school.util.GeneticAlgorithmConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    private static final int MAX_GENERATION_TIME_SECONDS = 300;

    private static final IClassService classService = new ClassServiceImpl();
    private static final ITeacherService teacherService = new TeacherServiceImpl();
    private static final ISubjectService subjectService = new SubjectServiceImpl();
    private static final IClassroomService classroomService = new ClassroomServiceImpl();
    private static final ILessonService lessonService = new LessonServiceImpl();

    public static void main(String[] args) {
        IValidationService validationService = new ValidationServiceImpl();


        // Get all classes
        List<SchoolClass> allClasses = getAllClasses();

        logger.info("Starting genetic algorithm with improved parameters...");
        logger.info("Population: {}, Generations: {}",
                GeneticAlgorithmConstants.POPULATION_SIZE,
                GeneticAlgorithmConstants.GENERATIONS_NUMBER);
        logger.info("Generating schedule for {} classes", allClasses.size());
        logger.info("Max generation time: {} seconds", MAX_GENERATION_TIME_SECONDS);

        // Generate optimal schedule using genetic algorithm
        IScheduler scheduler = new GeneticScheduler();

        logger.info("Please wait, this may take a few minutes...");
        long startTime = System.currentTimeMillis();

        SchoolClassesSchedule optimalSchedule = scheduler.generateScheduleFor(allClasses);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        if (duration > MAX_GENERATION_TIME_SECONDS) {
            logger.warn("Generation took longer than expected ({} seconds). Consider reducing parameters.", duration);
        } else {
            logger.info("Schedule generated in {} seconds", duration);
        }

        // Validate the generated schedule
        List<Lesson> allLessons = getAllLessonsFromSchedule(optimalSchedule);
        boolean isValid = validationService.validateSchedule(allLessons);

        if (isValid) {
            logger.info("Schedule validation PASSED");
        } else {
            logger.warn("Schedule validation FAILED with errors: {}", validationService.getValidationErrors());
        }

        int fitnessScore = validationService.fitness(optimalSchedule);
        logger.info("Final fitness score: {}/1000", fitnessScore);

        // Save lessons to database
        saveLessonsToDatabase(optimalSchedule);

        // Display the generated schedule
        displaySchedule(optimalSchedule);

        logger.info("Schedule generation completed successfully!");
    }

    private static List<SchoolClass> getAllClasses() {
        return classService.getAllClasses();
    }

    private static List<Lesson> getAllLessonsFromSchedule(SchoolClassesSchedule schedule) {
        List<Lesson> allLessons = new ArrayList<>();
        for (WeeklySchedule weeklySchedule : schedule.getAllSchoolClassesSchedule()) {
            for (DailySchedule dailySchedule : weeklySchedule.getWeeklySchedule()) {
                allLessons.addAll(dailySchedule.getDailySchedule());
            }
        }
        return allLessons;
    }

    private static void saveLessonsToDatabase(SchoolClassesSchedule schedule) {
        int lessonsCount = 0;

        for (WeeklySchedule weeklySchedule : schedule.getAllSchoolClassesSchedule()) {
            for (DailySchedule dailySchedule : weeklySchedule.getWeeklySchedule()) {
                for (Lesson lesson : dailySchedule.getDailySchedule()) {
                    lessonService.createLesson(lesson);
                    lessonsCount++;
                }
            }
        }

        logger.info("Successfully saved {} lessons to database", lessonsCount);
    }

    private static void displaySchedule(SchoolClassesSchedule schedule) {
        logger.info("=".repeat(80));
        logger.info("FINAL OPTIMAL SCHOOL SCHEDULE");
        logger.info("=".repeat(80));

        for (WeeklySchedule weeklySchedule : schedule.getAllSchoolClassesSchedule()) {
            // We get the first lesson for defining a class
            int classId = getClassIdFromSchedule(weeklySchedule);
            SchoolClass schoolClass = classService.getClassById(classId);

            if (schoolClass != null) {
                logger.info("SCHEDULE FOR {}:", schoolClass.getName().toUpperCase());
                logger.info("-".repeat(50));

                int dayIndex = 1;
                for (DailySchedule dailySchedule : weeklySchedule.getWeeklySchedule()) {
                    logger.info("DAY {}:", dayIndex);

                    int lessonNum = 1;
                    for (Lesson lesson : dailySchedule.getDailySchedule()) {
                        // We get names instead of IDs
                        String subjectName = subjectService.getSubjectById(lesson.getSubjectId()).getName();
                        String teacherLastName = teacherService.getTeacherById(lesson.getTeacherId()).getLastName();
                        String teacherFirstName = teacherService.getTeacherById(lesson.getTeacherId()).getFirstName();
                        String roomNumber = classroomService.getClassroomById(lesson.getClassroomId()).getRoomNumber();

                        logger.info("   Lesson {}. {} - {} ({} {}), Room: {}",
                                lessonNum,
                                getTimeByLessonNumber(lessonNum),
                                subjectName,
                                teacherLastName,
                                teacherFirstName,
                                roomNumber);
                        lessonNum++;
                    }
                    dayIndex++;
                }
                logger.info("─".repeat(50));
            } else {
                logger.warn("Class with id {} not found!", classId);
            }
        }
    }

    // Auxiliary method for getting lesson time
    private static String getTimeByLessonNumber(int lessonNumber) {
        return switch (lessonNumber) {
            case 1 -> "8:30-9:15";
            case 2 -> "9:25-10:10";
            case 3 -> "10:25-11:10";
            case 4 -> "11:25-12:10";
            case 5 -> "12:25-13:10";
            case 6 -> "13:20-14:05";
            default -> "Unknown";
        };
    }

    // Helper method for getting class ID from timetable
    private static int getClassIdFromSchedule(WeeklySchedule weeklySchedule) {
        if (!weeklySchedule.getWeeklySchedule().isEmpty() &&
                !weeklySchedule.getWeeklySchedule().get(0).getDailySchedule().isEmpty()) {
            return weeklySchedule.getWeeklySchedule().get(0).getDailySchedule().get(0).getClassId();
        }
        return 1; // fallback
    }
}