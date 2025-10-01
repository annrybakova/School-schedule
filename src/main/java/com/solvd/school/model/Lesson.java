package com.solvd.school.model;

public class Lesson {
    private int id;
    private int classId;
    private int subjectId;
    private int teacherId;
    private int classroomId;
    private int dayOfWeek;
    private int lessonNumber;
    private SchoolClass schoolClass;
    private Subject subject;
    private Teacher teacher;
    private Classroom classroom;

    public Lesson() {}

    public Lesson(int classId, int subjectId, int teacherId, int classroomId,
                  int dayOfWeek, int lessonNumber) {
        this.classId = classId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.classroomId = classroomId;
        this.dayOfWeek = dayOfWeek;
        this.lessonNumber = lessonNumber;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getClassroomId() { return classroomId; }
    public void setClassroomId(int classroomId) { this.classroomId = classroomId; }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getLessonNumber() { return lessonNumber; }
    public void setLessonNumber(int lessonNumber) { this.lessonNumber = lessonNumber; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Classroom getClassroom() { return classroom; }
    public void setClassroom(Classroom classroom) { this.classroom = classroom; }

    @Override
    public String toString() {
        return "Lesson{id=" + id + ", classId=" + classId + ", subjectId=" + subjectId +
                ", teacherId=" + teacherId + ", classroomId=" + classroomId +
                ", dayOfWeek=" + dayOfWeek + ", lessonNumber=" + lessonNumber + "}";
    }
}