package com.solvd.school.util;

import java.util.Random;

public class Randomizer {
    public static int randomNum(int start, int end) {
        Random rand = new Random();
        return rand.nextInt(end - start + 1) + start;
    }
}
