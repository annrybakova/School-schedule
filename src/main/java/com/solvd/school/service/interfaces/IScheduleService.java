package com.solvd.school.service.interfaces;

import com.solvd.school.model.Lesson;
import com.solvd.school.model.ScheduleGenerationLog;
import java.util.List;

public interface IScheduleService {
    void generateSchedule();

    List<Lesson> getClassSchedule(int classId);

    List<Lesson> getTeacherSchedule(int teacherId);

    List<Lesson> getClassroomSchedule(int classroomId);

    boolean saveGeneratedSchedule(List<Lesson> lessons);

    ScheduleGenerationLog getLastGenerationLog();
}