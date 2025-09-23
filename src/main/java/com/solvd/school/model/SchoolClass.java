package com.solvd.school.model;

import java.util.ArrayList;
import java.util.List;

public class SchoolClass {
    private int id;
    private String name;
    private final List<Student> students = new ArrayList<>();

    public SchoolClass() {
    }

    public SchoolClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getStudentCount() {
        return students.size();
    }

    @Override
    public String toString() {
        return "SchoolClass{id=" + id + ", name='" + name + "', students=" + students.size() + "}";
    }

}