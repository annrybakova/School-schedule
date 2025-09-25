package com.solvd.school.generator.lessonGenerator.interfaces;

import com.solvd.school.model.Lesson;

public interface ILessonGenerator {
    Lesson getLessonFor(int classId, int lessonNumber, int dayNumber);
}
