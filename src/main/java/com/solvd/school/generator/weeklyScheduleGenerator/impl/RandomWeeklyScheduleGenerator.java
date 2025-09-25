package com.solvd.school.generator.weeklyScheduleGenerator.impl;

import com.solvd.school.generator.dailyScheduleGenerator.impl.RandomDailyScheduleGenerator;
import com.solvd.school.generator.dailyScheduleGenerator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.weeklyScheduleGenerator.interfaces.IWeeklyScheduleGenerator;
import com.solvd.school.model.schedule.WeeklySchedule;
import com.solvd.school.util.ScheduleConstants;

public class RandomWeeklyScheduleGenerator implements IWeeklyScheduleGenerator {
    @Override
    public WeeklySchedule getWeeklyScheduleFor(int classId) {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        IDailyScheduleGenerator dailyScheduleGenerator = new RandomDailyScheduleGenerator();
        for(int i = 0; i < ScheduleConstants.DAYS_FOR_STUDYING; ++i) {
            weeklySchedule.addDailySchedule(dailyScheduleGenerator.getDailyScheduleFor(classId, i + 1));
        }

        return weeklySchedule;
    }
}
