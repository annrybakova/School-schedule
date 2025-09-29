package com.solvd.school.util;

public class GeneticAlgorithmConstants {

    private GeneticAlgorithmConstants() {
    }

    public static final int POPULATION_SIZE = 5;
    public static final int GENERATIONS_NUMBER = 10;

    public static final int PE_FIRST_LESSON_PENALTY = 10;
    public static final int TEACHER_GAP_PENALTY = 20;
    public static final int SUBJECT_COLLISION_PENALTY = 30;
    public static final int SPECIAL_ROOM_PENALTY = 15;
    public static final int TEACHER_OVERLOAD_PENALTY = 50;
    public static final int MATH_FORBIDDEN_PENALTY = 50;
    public static final int CLASS_GAP_PENALTY = 10;
    public static final int CRITICAL_TEACHER_OVERLOAD_PENALTY = 100;

    public static final int PERFECT_MATCHING_SCHEDULE_SCORE = 1000;
    public static final int TOURNAMENT_SIZE = 2;
    public static final double MUTATION_RATE = 0.05;
}
