package com.solvd.school.scheduler.impl;

import com.solvd.school.generator.impl.RandomSchoolClassesScheduleGenerator;
import com.solvd.school.generator.interfaces.ISchoolClassesScheduleGenerator;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.SchoolClassesSchedule;
import com.solvd.school.scheduler.interfaces.IScheduler;
import com.solvd.school.util.GeneticAlgorithmConstants;

import java.util.ArrayList;
import java.util.List;

public class GeneticScheduler implements IScheduler {
    @Override
    public SchoolClassesSchedule generateScheduleFor(List<SchoolClass> allClasses) {
        List<SchoolClassesSchedule> population = initPopulation(allClasses);

        return null;
    }

    private List<SchoolClassesSchedule> initPopulation(List<SchoolClass> allClasses) {
        List<SchoolClassesSchedule> population = new ArrayList<>();

        ISchoolClassesScheduleGenerator scheduleGenerator = new RandomSchoolClassesScheduleGenerator();
        for(int i = 0; i < GeneticAlgorithmConstants.POPULATION_SIZE; ++i) {
            SchoolClassesSchedule schoolClassesSchedule = scheduleGenerator.getSchoolClassesScheduleFor(allClasses);
            population.add(schoolClassesSchedule);
        }

        return population;
    }
}
