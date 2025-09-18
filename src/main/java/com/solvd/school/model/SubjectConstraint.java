package com.solvd.school.model;

public class SubjectConstraint {
    private int id;
    private int subjectId;
    private boolean notFirstLesson;
    private boolean notLastLesson;
    private Integer preferredLesson;
    private int maxLessonsPerDay;
    private Subject subject; // For join

    public SubjectConstraint() {}

    public SubjectConstraint(int id, int subjectId, boolean notFirstLesson, boolean notLastLesson,
                             Integer preferredLesson, int maxLessonsPerDay) {
        this.id = id;
        this.subjectId = subjectId;
        this.notFirstLesson = notFirstLesson;
        this.notLastLesson = notLastLesson;
        this.preferredLesson = preferredLesson;
        this.maxLessonsPerDay = maxLessonsPerDay;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public boolean isNotFirstLesson() { return notFirstLesson; }
    public void setNotFirstLesson(boolean notFirstLesson) { this.notFirstLesson = notFirstLesson; }

    public boolean isNotLastLesson() { return notLastLesson; }
    public void setNotLastLesson(boolean notLastLesson) { this.notLastLesson = notLastLesson; }

    public Integer getPreferredLesson() { return preferredLesson; }
    public void setPreferredLesson(Integer preferredLesson) { this.preferredLesson = preferredLesson; }

    public int getMaxLessonsPerDay() { return maxLessonsPerDay; }
    public void setMaxLessonsPerDay(int maxLessonsPerDay) { this.maxLessonsPerDay = maxLessonsPerDay; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    @Override
    public String toString() {
        return "SubjectConstraint{id=" + id + ", subjectId=" + subjectId + ", notFirstLesson=" + notFirstLesson +
                ", notLastLesson=" + notLastLesson + ", preferredLesson=" + preferredLesson +
                ", maxLessonsPerDay=" + maxLessonsPerDay + "}";
    }
}
