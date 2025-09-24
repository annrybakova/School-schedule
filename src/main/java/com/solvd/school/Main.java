package com.solvd.school;

import com.solvd.school.model.SchoolClass;
import com.solvd.school.service.impl.ClassServiceImpl;
import com.solvd.school.service.interfaces.IClassService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        IClassService classService = new ClassServiceImpl();
        List<SchoolClass> allClasses = classService.getAllClasses();

        for(SchoolClass schoolClass : allClasses) {
            System.out.println(schoolClass);
        }
    }
}
