package com.solvd.school.model;

public class Subject {
    private int id;
    private String name;
    private boolean requiresSpecialRoom;

    public Subject() {}

    public Subject(int id, String name, boolean requiresSpecialRoom) {
        this.id = id;
        this.name = name;
        this.requiresSpecialRoom = requiresSpecialRoom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public boolean isRequiresSpecialRoom() { return requiresSpecialRoom; }

    @Override
    public String toString() {
        return "Subject{id=" + id + ", name='" + name + "', requiresSpecialRoom=" + requiresSpecialRoom + "}";
    }
}