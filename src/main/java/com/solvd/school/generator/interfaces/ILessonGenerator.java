package com.solvd.school.generator.interfaces;

import com.solvd.school.model.Lesson;

public interface ILessonGenerator {
    Lesson getLessonFor(int classId, int lessonNumber, int dayNumber);
}
