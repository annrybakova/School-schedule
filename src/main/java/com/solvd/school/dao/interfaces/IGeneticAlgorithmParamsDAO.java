package com.solvd.school.dao.interfaces;

import com.solvd.school.model.GeneticAlgorithmParams;

public interface IGeneticAlgorithmParamsDAO {
    GeneticAlgorithmParams getActive();

    void insert(GeneticAlgorithmParams params);

    void update(GeneticAlgorithmParams params);

    void delete(int id);
}
