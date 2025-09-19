package com.solvd.school.model;

public class SpecialClassroom {
    private int id;
    private int subjectId;
    private int classroomId;
    private Subject subject;
    private Classroom classroom;

    public SpecialClassroom() {}

    public SpecialClassroom(int id, int subjectId, int classroomId) {
        this.id = id;
        this.subjectId = subjectId;
        this.classroomId = classroomId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getClassroomId() { return classroomId; }
    public void setClassroomId(int classroomId) { this.classroomId = classroomId; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Classroom getClassroom() { return classroom; }
    public void setClassroom(Classroom classroom) { this.classroom = classroom; }

    @Override
    public String toString() {
        return "SpecialClassroom{id=" + id + ", subjectId=" + subjectId + ", classroomId=" + classroomId + "}";
    }
}
