package com.solvd.school;

import com.solvd.school.dao.mybatisimpl.*;
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
import com.solvd.school.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    private static final int MAX_GENERATION_TIME_SECONDS = 300;

    public static void main(String[] args) {

        // Service Initialization
        ILessonService lessonService = new LessonServiceImpl();
        IClassService classService = new ClassServiceImpl();
        IValidationService validationService = new ValidationServiceImpl();

        // Get all classes
        List<SchoolClass> allClasses = getAllClasses(classService);

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
        saveLessonsToDatabase(optimalSchedule, lessonService);

        // Display the generated schedule
        displaySchedule(optimalSchedule);

        logger.info("Schedule generation completed successfully!");
    }

    private static List<SchoolClass> getAllClasses(IClassService classService) {
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

    private static void saveLessonsToDatabase(SchoolClassesSchedule schedule, ILessonService lessonService) {
        int lessonsCount = 0;

        logger.info("Saving lessons to database...");

        for (WeeklySchedule weeklySchedule : schedule.getAllSchoolClassesSchedule()) {
            for (DailySchedule dailySchedule : weeklySchedule.getWeeklySchedule()) {
                for (Lesson lesson : dailySchedule.getDailySchedule()) {
                    lessonService.createLesson(lesson);
                    lessonsCount++;
                }
            }
        }

        logger.info("Saved {} lessons to database", lessonsCount);
    }

    private static void displaySchedule(SchoolClassesSchedule schedule) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FINAL OPTIMAL SCHOOL SCHEDULE");
        System.out.println("=".repeat(80));

        int classIndex = 1;
        for (WeeklySchedule weeklySchedule : schedule.getAllSchoolClassesSchedule()) {
            System.out.println("\nSCHEDULE FOR CLASS " + classIndex + ":");
            System.out.println("-".repeat(50));

            int dayIndex = 1;
            for (DailySchedule dailySchedule : weeklySchedule.getWeeklySchedule()) {
                System.out.println("\nDAY " + dayIndex + ":");

                for (Lesson lesson : dailySchedule.getDailySchedule()) {
                    System.out.printf("   Lesson %d: Class=%d, Subject=%d, Teacher=%d, Room=%d\n",
                            lesson.getLessonNumber(),
                            lesson.getClassId(),
                            lesson.getSubjectId(),
                            lesson.getTeacherId(),
                            lesson.getClassroomId());
                }
                dayIndex++;
            }
            System.out.println("\n" + "─".repeat(50));
            classIndex++;
        }
    }
}