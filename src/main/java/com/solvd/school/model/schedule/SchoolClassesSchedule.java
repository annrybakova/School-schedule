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

    public SchoolClassesSchedule copy() {
        SchoolClassesSchedule copy = new SchoolClassesSchedule();

        for (WeeklySchedule week : allSchoolClassesSchedule) {
            copy.addWeeklySchedule(week.copy());
        }

        return copy;
    }
}
