package com.solvd.school.model;

public class TeacherAvailability {
    private int id;
    private int teacherId;
    private int dayOfWeek;
    private boolean isAvailable;
    private Teacher teacher;

    public TeacherAvailability() {}

    public TeacherAvailability(int id, int teacherId, int dayOfWeek, boolean isAvailable) {
        this.id = id;
        this.teacherId = teacherId;
        this.dayOfWeek = dayOfWeek;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    @Override
    public String toString() {
        return "TeacherAvailability{id=" + id + ", teacherId=" + teacherId + ", dayOfWeek=" + dayOfWeek +
                ", isAvailable=" + isAvailable + "}";
    }
}