package com.solvd.school.dao.interfaces;

import com.solvd.school.model.GeneratedSchedule;
import java.util.List;

public interface IGeneratedSchedulesDAO {
    List<GeneratedSchedule> getByGeneration(int generationId);

    void insert(GeneratedSchedule gs);

    void delete(int id);
}
