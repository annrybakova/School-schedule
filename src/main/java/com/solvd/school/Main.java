package com.solvd.school;

import com.solvd.school.dao.mybatisimpl.ClassDAO;
import com.solvd.school.dao.mybatisimpl.ClassroomsDAO;
import com.solvd.school.dao.mybatisimpl.LessonDAO;
import com.solvd.school.dao.mybatisimpl.SubjectDAO;
import com.solvd.school.dao.mybatisimpl.TeacherDAO;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        List<SchoolClass> allClasses = getAllClasses();

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

    private static List<SchoolClass> getAllClasses() {
        IClassService classService = new ClassServiceImpl();
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
