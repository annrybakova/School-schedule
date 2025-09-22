package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import java.util.List;

public interface ILessonService {
    Lesson getLessonById(int id);
    List<Lesson> getLessonsByClass(int classId);
    List<Lesson> getLessonsByClassAndDay(int classId, int dayOfWeek);
    void createLesson(Lesson lesson);
    void updateLesson(Lesson lesson);
    void deleteLesson(int id);
    boolean isTimeSlotOccupied(int classId, int dayOfWeek, int lessonNumber);
    void insertRandomLesson();
}