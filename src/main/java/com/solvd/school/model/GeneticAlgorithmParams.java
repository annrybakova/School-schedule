package com.solvd.school.model;

public class GeneticAlgorithmParams {
    private int id;
    private int maxGenerations;
    private int allowedGaps;
    private int populationSize;
    private float mutationRate;
    private float crossoverRate;
    private String description;
    private boolean isActive;

    public GeneticAlgorithmParams() {}

    public GeneticAlgorithmParams(int id, int maxGenerations, int allowedGaps, int populationSize,
                                  float mutationRate, float crossoverRate, String description, boolean isActive) {
        this.id = id;
        this.maxGenerations = maxGenerations;
        this.allowedGaps = allowedGaps;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.description = description;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "GeneticAlgorithmParams{id=" + id + ", maxGenerations=" + maxGenerations +
                ", allowedGaps=" + allowedGaps + ", populationSize=" + populationSize +
                ", mutationRate=" + mutationRate + ", crossoverRate=" + crossoverRate +
                ", description='" + description + "', isActive=" + isActive + "}";
    }
}