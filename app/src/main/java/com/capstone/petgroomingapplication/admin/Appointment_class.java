package com.capstone.petgroomingapplication.admin;


public class Appointment_class {
    public String id;
    public String customerName;
    public String petName;
    public String petBreed;
    public String address;
    public String date;
    public String time;

    public Appointment_class() {
        // Default constructor required for calls to DataSnapshot.getValue(Appointment.class)
    }

    public Appointment_class(String id, String customerName, String petName, String petBreed, String address, String date, String time) {
        this.id = id;
        this.customerName = customerName;
        this.petName = petName;
        this.petBreed = petBreed;
        this.address = address;
        this.date = date;
        this.time = time;
    }
}
