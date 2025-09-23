package com.solvd.school.generator.weeklyScheduleGenerator.impl;

import com.solvd.school.generator.dailyScheduleGenerator.impl.RandomDailyScheduleGenerator;
import com.solvd.school.generator.dailyScheduleGenerator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.weeklyScheduleGenerator.interfaces.IWeeklyScheduleGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.util.ScheduleConstants;

import java.util.ArrayList;
import java.util.List;

public class RandomWeeklyScheduleGenerator implements IWeeklyScheduleGenerator {
    @Override
    public List<List<Lesson>> getWeeklyScheduleFor(int classId) {
        List<List<Lesson>> weeklySchedule = new ArrayList<>();

        IDailyScheduleGenerator dailyScheduleGenerator = new RandomDailyScheduleGenerator();
        for(int i = 0; i < ScheduleConstants.DAYS_FOR_STUDYING; ++i) {
            weeklySchedule.add(dailyScheduleGenerator.getDailyScheduleFor(classId));
        }

        return weeklySchedule;
    }
}
