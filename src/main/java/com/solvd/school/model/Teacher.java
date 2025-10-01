package com.solvd.school.model;

public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private int subjectId;
    private int maxLessonsPerDay;
    private Subject subject;

    public Teacher() {}

    public Teacher(int id, String firstName, String lastName, int subjectId, int maxLessonsPerDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjectId = subjectId;
        this.maxLessonsPerDay = maxLessonsPerDay;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLastName() { return lastName; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getMaxLessonsPerDay() { return maxLessonsPerDay; }
    public void setMaxLessonsPerDay(int maxLessonsPerDay) { this.maxLessonsPerDay = maxLessonsPerDay; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    @Override
    public String toString() {
        return "Teacher{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', subjectId=" + subjectId + ", maxLessonsPerDay=" + maxLessonsPerDay + "}";
    }
}
