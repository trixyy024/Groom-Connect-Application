package com.capstone.petgroomingapplication.model;

import com.google.firebase.Timestamp;

public class UserModel {

    private String phoneNumber;
    private String username;
    private Timestamp registrationDate;
    private String userId;
    private String password;
    private String contactNo;
    private String address;
    private String profilePicUrl; // Add this field for the profile picture URL
    private static String collection;
    // Default constructor required for Firestore serialization
    public UserModel() { }

    public UserModel(String phoneNumber, String username, Timestamp registrationDate, String userId, String password, String contactNo, String address, String profilePicUrl) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.registrationDate = registrationDate;
        this.userId = userId;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
        this.profilePicUrl = profilePicUrl; // Initialize this field
       this.collection = collection;
    }
    public static String getCollection() { return collection; }
    public static void setCollection(String collection) { collection = collection; }


    // Getters and Setters for all fields
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Timestamp getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Timestamp registrationDate) { this.registrationDate = registrationDate; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public  String getProfilePicUrl() { return profilePicUrl; } // Getter for profile picture URL
    public void setProfilePicUrl(String profilePicUrl) { this.profilePicUrl = profilePicUrl; } // Setter for profile picture URL
}
