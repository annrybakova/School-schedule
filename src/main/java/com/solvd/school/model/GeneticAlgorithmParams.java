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

    public int getMaxGenerations() { return maxGenerations; }
    public void setMaxGenerations(int maxGenerations) { this.maxGenerations = maxGenerations; }

    public int getAllowedGaps() { return allowedGaps; }
    public void setAllowedGaps(int allowedGaps) { this.allowedGaps = allowedGaps; }

    public int getPopulationSize() { return populationSize; }
    public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }

    public float getMutationRate() { return mutationRate; }
    public void setMutationRate(float mutationRate) { this.mutationRate = mutationRate; }

    public float getCrossoverRate() { return crossoverRate; }
    public void setCrossoverRate(float crossoverRate) { this.crossoverRate = crossoverRate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "GeneticAlgorithmParams{id=" + id + ", maxGenerations=" + maxGenerations +
                ", allowedGaps=" + allowedGaps + ", populationSize=" + populationSize +
                ", mutationRate=" + mutationRate + ", crossoverRate=" + crossoverRate +
                ", description='" + description + "', isActive=" + isActive + "}";
    }
}