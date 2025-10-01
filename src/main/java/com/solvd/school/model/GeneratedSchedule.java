package com.solvd.school.model;

public class GeneratedSchedule {
    private int id;
    private int generationId;
    private int lessonId;
    private ScheduleGenerationLog generationLog; // For join
    private Lesson lesson; // For join

    public GeneratedSchedule() {}

    public GeneratedSchedule(int id, int generationId, int lessonId) {
        this.id = id;
        this.generationId = generationId;
        this.lessonId = lessonId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }

    @Override
    public String toString() {
        return "GeneratedSchedule{id=" + id + ", generationId=" + generationId + ", lessonId=" + lessonId + "}";
    }
}