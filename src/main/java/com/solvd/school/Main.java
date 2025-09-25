package com.solvd.school;

import com.solvd.school.dao.mybatisimpl.*;
import com.solvd.school.generator.weeklyScheduleGenerator.impl.RandomWeeklyScheduleGenerator;
import com.solvd.school.generator.weeklyScheduleGenerator.interfaces.IWeeklyScheduleGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.DailySchedule;
import com.solvd.school.model.schedule.WeeklySchedule;
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
import com.solvd.school.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();

        LessonDAO lessonDAO = new LessonDAO(sqlSessionFactory);
        TeacherDAO teacherDAO = new TeacherDAO(sqlSessionFactory);
        SubjectDAO subjectDAO = new SubjectDAO(sqlSessionFactory);
        ClassSubjectDAO classSubjectDAO = new ClassSubjectDAO(sqlSessionFactory);
        ClassroomsDAO classroomDAO = new ClassroomsDAO(sqlSessionFactory);
        ClassDAO classDAO = new ClassDAO(sqlSessionFactory);
        SpecialClassroomDAO specialClassroomDAO = new SpecialClassroomDAO(sqlSessionFactory);

        // Service Initialization
        ILessonService lessonService = new LessonServiceImpl(lessonDAO);
        ITeacherService teacherService = new TeacherServiceImpl(teacherDAO, lessonDAO);
        ISubjectService subjectService = new SubjectServiceImpl(subjectDAO, classSubjectDAO);
        IClassroomService classroomService = new ClassroomServiceImpl(classroomDAO, lessonDAO, specialClassroomDAO);
        IClassService classService = new ClassServiceImpl(classDAO);

        IValidationService validationService = new ValidationServiceImpl(
                teacherService, subjectService, classroomService, classService
        );

         List<SchoolClass> allClasses = getAllClasses(classService);

        // for (SchoolClass schoolClass : allClasses) {
        // System.out.println(schoolClass);
        // }

        List<WeeklySchedule> weeklySchedules = getWeeklySchedulesFor(allClasses);

        for (int i = 0; i < weeklySchedules.size(); ++i) {
            System.out.println("Schedule of " + (i + 1) + "-group:\n");

            for (DailySchedule dailySchedule : weeklySchedules.get(i).getWeeklySchedule()) {
                for (Lesson lesson : dailySchedule.getDailySchedule()) {
                    System.out.println(lesson);
                }
                System.out.println();
            }
        }
    }

    private static List<SchoolClass> getAllClasses(IClassService classService) {
        // IClassService classService = new ClassServiceImpl();
        return classService.getAllClasses();
    }

    private static List<WeeklySchedule> getWeeklySchedulesFor(List<SchoolClass> allClasses) {
        List<WeeklySchedule> weeklySchedules = new ArrayList<>();

        IWeeklyScheduleGenerator weeklyScheduleGenerator = new RandomWeeklyScheduleGenerator();
        for (SchoolClass schoolClass : allClasses) {
            int schoolClassId = schoolClass.getId();
            WeeklySchedule weeklySchedule = weeklyScheduleGenerator.getWeeklyScheduleFor(schoolClassId);
            weeklySchedules.add(weeklySchedule);
        }

        return weeklySchedules;
    }
}
