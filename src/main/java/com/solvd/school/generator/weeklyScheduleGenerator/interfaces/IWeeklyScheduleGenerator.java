package com.solvd.school.generator.weeklyScheduleGenerator.interfaces;

import com.solvd.school.model.Lesson;

import java.util.List;

public interface IWeeklyScheduleGenerator {
    List<List<Lesson>> getWeeklyScheduleFor(int classId);
}
