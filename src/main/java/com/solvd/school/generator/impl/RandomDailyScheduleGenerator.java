package com.solvd.school.generator.impl;

import com.solvd.school.generator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.interfaces.ILessonGenerator;
import com.solvd.school.model.schedule.DailySchedule;
import com.solvd.school.util.ScheduleConstants;

public class RandomDailyScheduleGenerator implements IDailyScheduleGenerator {

    @Override
    public DailySchedule getDailyScheduleFor(int classId, int dayNumber) {
        DailySchedule dailySchedule = new DailySchedule();

        ILessonGenerator generator = new RandomLessonGenerator();
        for(int i = 1; i <= ScheduleConstants.DAILY_LESSONS_NUMBER; ++i) {
            dailySchedule.addLesson(generator.getLessonFor(classId, i, dayNumber));
        }

        return dailySchedule;
    }
}
