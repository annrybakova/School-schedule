package com.solvd.school.model;

import java.util.Date;

public class ScheduleGenerationLog {
    private int id;
    private Date generationDate;
    private String status;
    private int generationsExecuted;
    private float bestFitnessScore;
    private Integer algorithmParamsId;
    private String message;
    private GeneticAlgorithmParams algorithmParams;

    public ScheduleGenerationLog() {}

    public ScheduleGenerationLog(int id, Date generationDate, String status, int generationsExecuted,
                                 float bestFitnessScore, Integer algorithmParamsId, String message) {
        this.id = id;
        this.generationDate = generationDate;
        this.status = status;
        this.generationsExecuted = generationsExecuted;
        this.bestFitnessScore = bestFitnessScore;
        this.algorithmParamsId = algorithmParamsId;
        this.message = message;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "ScheduleGenerationLog{id=" + id + ", generationDate=" + generationDate +
                ", status='" + status + "', generationsExecuted=" + generationsExecuted +
                ", bestFitnessScore=" + bestFitnessScore + ", algorithmParamsId=" + algorithmParamsId +
                ", message='" + message + "'}";
    }
}