package com.solvd.school.model;

public class Classroom {
    private int id;
    private String roomNumber;
    private boolean isSpecial;
    private int capacity;

    public Classroom() {}

    public Classroom(int id, String roomNumber, boolean isSpecial, int capacity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.isSpecial = isSpecial;
        this.capacity = capacity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public boolean isSpecial() { return isSpecial; }
    public void setSpecial(boolean special) { isSpecial = special; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return "Classroom{id=" + id + ", roomNumber='" + roomNumber + "', isSpecial=" + isSpecial +
                ", capacity=" + capacity + "}";
    }
}
