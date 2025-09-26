package com.solvd.school.generator.impl;

import com.solvd.school.generator.interfaces.ISchoolClassesScheduleGenerator;
import com.solvd.school.generator.interfaces.IWeeklyScheduleGenerator;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.SchoolClassesSchedule;
import com.solvd.school.model.schedule.WeeklySchedule;

import java.util.List;

public class RandomSchoolClassesScheduleGenerator implements ISchoolClassesScheduleGenerator {
    @Override
    public SchoolClassesSchedule getSchoolClassesScheduleFor(List<SchoolClass> allClasses) {
        SchoolClassesSchedule schoolClassesSchedule = new SchoolClassesSchedule();

        IWeeklyScheduleGenerator weeklyScheduleGenerator = new RandomWeeklyScheduleGenerator();
        for (SchoolClass schoolClass : allClasses) {
            int schoolClassId = schoolClass.getId();
            WeeklySchedule weeklySchedule = weeklyScheduleGenerator.getWeeklyScheduleFor(schoolClassId);
            schoolClassesSchedule.addWeeklySchedule(weeklySchedule);
        }

        return schoolClassesSchedule;
    }
}
