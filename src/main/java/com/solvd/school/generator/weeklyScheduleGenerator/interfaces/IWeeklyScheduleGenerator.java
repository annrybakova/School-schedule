package com.solvd.school.generator.weeklyScheduleGenerator.interfaces;

import com.solvd.school.model.schedule.WeeklySchedule;

public interface IWeeklyScheduleGenerator {
    WeeklySchedule getWeeklyScheduleFor(int classId);
}
