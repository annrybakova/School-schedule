package com.solvd.school.generator.dailyScheduleGenerator.interfaces;

import com.solvd.school.model.Lesson;

import java.util.List;

public interface IDailyScheduleGenerator {
    List<Lesson> getDailyScheduleFor(int classId);
}
