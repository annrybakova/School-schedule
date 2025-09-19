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

    public Date getGenerationDate() { return generationDate; }
    public void setGenerationDate(Date generationDate) { this.generationDate = generationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getGenerationsExecuted() { return generationsExecuted; }
    public void setGenerationsExecuted(int generationsExecuted) { this.generationsExecuted = generationsExecuted; }

    public float getBestFitnessScore() { return bestFitnessScore; }
    public void setBestFitnessScore(float bestFitnessScore) { this.bestFitnessScore = bestFitnessScore; }

    public Integer getAlgorithmParamsId() { return algorithmParamsId; }
    public void setAlgorithmParamsId(Integer algorithmParamsId) { this.algorithmParamsId = algorithmParamsId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public GeneticAlgorithmParams getAlgorithmParams() { return algorithmParams; }
    public void setAlgorithmParams(GeneticAlgorithmParams algorithmParams) { this.algorithmParams = algorithmParams; }

    @Override
    public String toString() {
        return "ScheduleGenerationLog{id=" + id + ", generationDate=" + generationDate +
                ", status='" + status + "', generationsExecuted=" + generationsExecuted +
                ", bestFitnessScore=" + bestFitnessScore + ", algorithmParamsId=" + algorithmParamsId +
                ", message='" + message + "'}";
    }
}