package com.solvd.school.model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int classId;
    private int groupId;
    private SchoolClass schoolClass; // For join
    private StudentGroup group; // For join

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

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }

    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }

    public StudentGroup getGroup() { return group; }
    public void setGroup(StudentGroup group) { this.group = group; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', classId=" + classId + ", groupId=" + groupId + "}";
    }
}