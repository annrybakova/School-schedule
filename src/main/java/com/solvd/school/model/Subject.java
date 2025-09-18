package com.solvd.school.model;

public class Subject {
    private int id;
    private String name;
    private boolean requiresSpecialRoom;
    private boolean hasStudentGroups;

    public Subject() {}

    public Subject(int id, String name, boolean requiresSpecialRoom, boolean hasStudentGroups) {
        this.id = id;
        this.name = name;
        this.requiresSpecialRoom = requiresSpecialRoom;
        this.hasStudentGroups = hasStudentGroups;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isRequiresSpecialRoom() { return requiresSpecialRoom; }
    public void setRequiresSpecialRoom(boolean requiresSpecialRoom) { this.requiresSpecialRoom = requiresSpecialRoom; }

    public boolean isHasStudentGroups() { return hasStudentGroups; }
    public void setHasStudentGroups(boolean hasStudentGroups) { this.hasStudentGroups = hasStudentGroups; }

    @Override
    public String toString() {
        return "Subject{id=" + id + ", name='" + name + "', requiresSpecialRoom=" + requiresSpecialRoom +
                ", hasStudentGroups=" + hasStudentGroups + "}";
    }
}