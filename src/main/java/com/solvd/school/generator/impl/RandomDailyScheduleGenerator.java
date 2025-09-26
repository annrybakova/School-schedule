package com.solvd.school.generator.impl;

import com.solvd.school.generator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.interfaces.ILessonGenerator;
import com.solvd.school.model.schedule.DailySchedule;
import com.solvd.school.util.ScheduleConstants;

public class RandomDailyScheduleGenerator implements IDailyScheduleGenerator {

    @Override
    public DailySchedule getDailyScheduleFor(int classId, int dayNumber) {
        DailySchedule dailySchedule = new DailySchedule();

        ILessonGenerator lessonGenerator = new RandomLessonGenerator();
        for(int i = 0; i < ScheduleConstants.DAILY_LESSONS_NUMBER; ++i) {
            dailySchedule.addLesson(lessonGenerator.getLessonFor(classId, i + 1, dayNumber));
        }

        return dailySchedule;
    }
}
