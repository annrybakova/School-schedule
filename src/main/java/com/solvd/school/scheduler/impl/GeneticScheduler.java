package com.solvd.school.scheduler.impl;

import com.solvd.school.generator.impl.RandomSchoolClassesScheduleGenerator;
import com.solvd.school.generator.interfaces.ISchoolClassesScheduleGenerator;
import com.solvd.school.model.SchoolClass;
import com.solvd.school.model.schedule.SchoolClassesSchedule;
import com.solvd.school.model.schedule.WeeklySchedule;
import com.solvd.school.scheduler.interfaces.IScheduler;
import com.solvd.school.util.GeneticAlgorithmConstants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneticScheduler implements IScheduler {
    @Override
    public SchoolClassesSchedule generateScheduleFor(List<SchoolClass> allClasses) {
        List<SchoolClassesSchedule> population = initPopulation(allClasses);

        for (int i = 0; i < GeneticAlgorithmConstants.GENERATIONS_NUMBER; ++i) {
            population.sort(Comparator.comparingInt(this::fitness).reversed());

            if (fitness(population.get(0)) == GeneticAlgorithmConstants.PERFECT_MATCHING_SCHEDULE_SCORE) {
                return population.get(0);
            }

            List<SchoolClassesSchedule> newPopulation = new ArrayList<>();

            while (newPopulation.size() < population.size()) {
                SchoolClassesSchedule parent1 = select(population);
                SchoolClassesSchedule parent2 = select(population);
                SchoolClassesSchedule child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }
        }

        return population.get(0);
    }

    private List<SchoolClassesSchedule> initPopulation(List<SchoolClass> allClasses) {
        List<SchoolClassesSchedule> population = new ArrayList<>();

        ISchoolClassesScheduleGenerator scheduleGenerator = new RandomSchoolClassesScheduleGenerator();
        for (int i = 0; i < GeneticAlgorithmConstants.POPULATION_SIZE; ++i) {
            SchoolClassesSchedule schoolClassesSchedule = scheduleGenerator.getSchoolClassesScheduleFor(allClasses);
            population.add(schoolClassesSchedule);
        }

        return population;
    }

    private int fitness(SchoolClassesSchedule schedule) {
        int score = GeneticAlgorithmConstants.PERFECT_MATCHING_SCHEDULE_SCORE;

        return score;
    }

    private SchoolClassesSchedule select(List<SchoolClassesSchedule> population) {
        List<SchoolClassesSchedule> tournament = new ArrayList<>();

        for (int i = 0; i < GeneticAlgorithmConstants.TOURNAMENT_SIZE; ++i) {
            int randomIndex = (int) (Math.random() * population.size());
            tournament.add(population.get(randomIndex));
        }

        return tournament.stream()
                .max(Comparator.comparingInt(this::fitness))
                .orElse(population.get(0));
    }

    private SchoolClassesSchedule crossover(SchoolClassesSchedule parent1, SchoolClassesSchedule parent2) {
        SchoolClassesSchedule child = new SchoolClassesSchedule();

        List<WeeklySchedule> parent1Schedules = parent1.getAllSchoolClassesSchedule();
        List<WeeklySchedule> parent2Schedules = parent2.getAllSchoolClassesSchedule();

        int size = parent1Schedules.size();
        int crossoverPoint = (int) (Math.random() * size);

        for(int i = 0; i < crossoverPoint; ++i) {
            child.addWeeklySchedule(parent1Schedules.get(i).copy());
        }

        for(int i = crossoverPoint; i < size; ++i) {
            child.addWeeklySchedule(parent2Schedules.get(i).copy());
        }

        return child;
    }

    private void mutate(SchoolClassesSchedule child) {
    }
}