package com.solvd.school.generator.dailyScheduleGenerator.impl;

import com.solvd.school.generator.dailyScheduleGenerator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.lessonGenerator.impl.RandomLessonGenerator;
import com.solvd.school.generator.lessonGenerator.interfaces.ILessonGenerator;
import com.solvd.school.model.schedule.DailySchedule;
import com.solvd.school.util.ScheduleConstants;

public class RandomDailyScheduleGenerator implements IDailyScheduleGenerator {

    @Override
    public DailySchedule getDailyScheduleFor(int classId) {
        DailySchedule dailySchedule = new DailySchedule();

        ILessonGenerator lessonGenerator = new RandomLessonGenerator();
        for(int i = 0; i < ScheduleConstants.DAILY_LESSONS_NUMBER; ++i) {
            dailySchedule.addLesson(lessonGenerator.getLessonFor(classId));
        }

        return dailySchedule;
    }
}
