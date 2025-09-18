package com.solvd.school.model;

public class ClassSubject {
    private int id;
    private int classId;
    private int subjectId;
    private int lessonsPerWeek;
    private boolean usesGroups;
    private SchoolClass schoolClass; // For join - renamed
    private Subject subject; // For join

    public ClassSubject() {}

    public ClassSubject(int id, int classId, int subjectId, int lessonsPerWeek, boolean usesGroups) {
        this.id = id;
        this.classId = classId;
        this.subjectId = subjectId;
        this.lessonsPerWeek = lessonsPerWeek;
        this.usesGroups = usesGroups;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getLessonsPerWeek() { return lessonsPerWeek; }
    public void setLessonsPerWeek(int lessonsPerWeek) { this.lessonsPerWeek = lessonsPerWeek; }

    public boolean isUsesGroups() { return usesGroups; }
    public void setUsesGroups(boolean usesGroups) { this.usesGroups = usesGroups; }

    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    @Override
    public String toString() {
        return "ClassSubject{id=" + id + ", classId=" + classId + ", subjectId=" + subjectId +
                ", lessonsPerWeek=" + lessonsPerWeek + ", usesGroups=" + usesGroups + "}";
    }
}