package com.solvd.school.model.schedule;

import java.util.ArrayList;
import java.util.List;

public class WeeklySchedule {
    private final List<DailySchedule> weeklySchedule = new ArrayList<>();

    public void addDailySchedule(DailySchedule dailySchedule) {
        weeklySchedule.add(dailySchedule);
    }

    public List<DailySchedule> getWeeklySchedule() {
        return weeklySchedule;
    }
}
