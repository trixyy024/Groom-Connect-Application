package com.capstone.petgroomingapplication.adapter;


public class Services_class_customer {
    private String name;
    private String price;
    private int imageResource;

    public Services_class_customer(String name, String price, int imageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }
}
