package com.solvd.school.dao.interfaces;

import com.solvd.school.model.Classroom;
import java.util.List;

public interface IClassroomsDAO {
    Classroom getById(int id);

    List<Classroom> getAll();

    void insert(Classroom classroom);

    void update(Classroom classroom);

    void delete(int id);
}
