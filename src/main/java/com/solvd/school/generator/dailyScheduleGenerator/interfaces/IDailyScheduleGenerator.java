package com.solvd.school.generator.dailyScheduleGenerator.interfaces;

import com.solvd.school.model.schedule.DailySchedule;

public interface IDailyScheduleGenerator {
    DailySchedule getDailyScheduleFor(int classId, int dayNumber);
}