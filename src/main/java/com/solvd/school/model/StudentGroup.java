package com.solvd.school.model;

public class StudentGroup {
    private int id;
    private String name;
    private int classId;
    private SchoolClass schoolClass; // For join - renamed

    public StudentGroup() {}

    public StudentGroup(int id, String name, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }

    @Override
    public String toString() {
        return "StudentGroup{id=" + id + ", name='" + name + "', classId=" + classId + "}";
    }
}