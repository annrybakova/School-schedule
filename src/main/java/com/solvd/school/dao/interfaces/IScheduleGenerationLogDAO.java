package com.solvd.school.dao.interfaces;

import com.solvd.school.model.ScheduleGenerationLog;

public interface IScheduleGenerationLogDAO {
    ScheduleGenerationLog getById(int id);

    void insert(ScheduleGenerationLog log);

    void update(ScheduleGenerationLog log);

    void delete(int id);
}
