package com.solvd.school.model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int classId;
    private int groupId;
    private SchoolClass schoolClass;

    public Student() {}

    public Student(int id, String firstName, String lastName, int classId, int groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classId = classId;
        this.groupId = groupId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', classId=" + classId + ", groupId=" + groupId + "}";
    }
}