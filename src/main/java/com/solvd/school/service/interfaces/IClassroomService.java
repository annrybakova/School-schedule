package com.solvd.school.service.interfaces;

import com.solvd.school.model.Classroom;
import java.util.List;

public interface IClassroomService {
    Classroom getClassroomById(int id);

    List<Classroom> getAllClassrooms();

    List<Classroom> getAvailableClassrooms(int dayOfWeek, int lessonNumber);

    boolean isClassroomAvailable(int classroomId, int dayOfWeek, int lessonNumber);

    Classroom getCommonClassroomForSubject(int subject);

    Classroom getSpecialClassroomForSubject(int subjectId);

    boolean isSubjectRoomConstraintViolated(int subjectId, int dayOfWeek);

    boolean hasEnoughCapacity(int classroomId, int studentCount);

    List<Classroom> getAllCommonClassrooms();

    List<Classroom> getAllSpecialClassrooms();

}