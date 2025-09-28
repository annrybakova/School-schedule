package com.solvd.school.model.schedule;

import com.solvd.school.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class DailySchedule {
    private final List<Lesson> dailySchedule = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        dailySchedule.add(lesson);
    }

    public List<Lesson> getDailySchedule() {
        return dailySchedule;
    }

    public DailySchedule copy() {
        DailySchedule copy = new DailySchedule();

        for (Lesson lesson : dailySchedule) {
            copy.addLesson(new Lesson(
                    lesson.getClassId(),
                    lesson.getSubjectId(),
                    lesson.getTeacherId(),
                    lesson.getClassroomId(),
                    lesson.getDayOfWeek(),
                    lesson.getLessonNumber()
            ));
        }

        return copy;
    }
}
