package com.solvd.school.generator.interfaces;

import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.SchoolClassesSchedule;

import java.util.List;

public interface ISchoolClassesScheduleGenerator {
    SchoolClassesSchedule getSchoolClassesScheduleFor(List<SchoolClass> allClasses);
}
