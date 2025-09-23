package com.solvd.school.generator.dailyScheduleGenerator.impl;

import com.solvd.school.generator.dailyScheduleGenerator.interfaces.IDailyScheduleGenerator;
import com.solvd.school.generator.lessonGenerator.impl.RandomLessonGenerator;
import com.solvd.school.generator.lessonGenerator.interfaces.ILessonGenerator;
import com.solvd.school.model.Lesson;
import com.solvd.school.util.ScheduleConstants;

import java.util.ArrayList;
import java.util.List;

public class RandomDailyScheduleGenerator implements IDailyScheduleGenerator {

    @Override
    public List<Lesson> getDailyScheduleFor(int classId) {
        List<Lesson> dailySchedule = new ArrayList<>();

        ILessonGenerator lessonGenerator = new RandomLessonGenerator();
        for(int i = 0; i < ScheduleConstants.DAILY_LESSONS_NUMBER; ++i) {
            dailySchedule.add(lessonGenerator.getLessonFor(classId));
        }

        return dailySchedule;
    }
}
