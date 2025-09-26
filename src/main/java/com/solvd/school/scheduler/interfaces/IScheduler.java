package com.solvd.school.scheduler.interfaces;

import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.SchoolClassesSchedule;

import java.util.List;

public interface IScheduler {
    SchoolClassesSchedule generateScheduleFor(List<SchoolClass> allClasses);
}
