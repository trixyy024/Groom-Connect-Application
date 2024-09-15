package com.capstone.petgroomingapplication.adapter;

public class Pet_class_admin {
    private String name;
    private String breed;
    private String age;
    private String history;
    private int timesService;
    private String owner;
    private String location;
    private String notes;
    private int imageResource;

    // Constructor, getters and setters

   /** public Pet(String name, String breed, String age, String history, int timesService, String owner, String location, String notes) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.history = history;
        this.timesService = timesService;
        this.owner = owner;
        this.location = location;
        this.notes = notes;
    }
    **/

    public Pet_class_admin(int imageResource, String name, String breed) {
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return Integer.parseInt(age);
    }

    public String getHistory() {
        return history;
    }

    public int getTimesService() {
        return timesService;
    }

    public String getOwner() {
        return owner;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }
    public int getImageResource() {

        return imageResource;
    }

    // Getters and setters
}
