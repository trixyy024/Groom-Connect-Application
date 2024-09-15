package com.capstone.petgroomingapplication.admin;

public class ServiceClass {
    private String serviceID;
    private String serviceName;
    private String category;
    private String price;

    // Default constructor required for calls to DataSnapshot.getValue(ServiceClass.class)

    public ServiceClass(String serviceID, String serviceName, String category, String price) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.category = category;
        this.price = price;
    }

    public int getCode() {
        return 0;
    }

    public int getName() {
        return 0;
    }

    public int getCategory() {
        return 0;
    }

    public int getPrice() {
        return 0;
    }
}
