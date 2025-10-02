package com.solvd.school.model.schedule;

import java.util.ArrayList;
import java.util.List;

public class SchoolClassesSchedule {
    private final List<WeeklySchedule> allSchoolClassesSchedule = new ArrayList<>();

    public void addWeeklySchedule(WeeklySchedule weeklySchedule) {
        allSchoolClassesSchedule.add(weeklySchedule);
    }

    public List<WeeklySchedule> getAllSchoolClassesSchedule() {
        return allSchoolClassesSchedule;
    }
}
