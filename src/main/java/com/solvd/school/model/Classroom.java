package com.solvd.school.model;

public class Classroom {
    private int id;
    private String roomNumber;
    private boolean isSpecial;
    private int capacity;

    public Classroom() {
    }

    public Classroom(int id, String roomNumber, boolean isSpecial, int capacity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.isSpecial = isSpecial;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Classroom{id=" + id + ", roomNumber='" + roomNumber + "', isSpecial=" + isSpecial +
                ", capacity=" + capacity + "}";
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
