package com.capstone.petgroomingapplication.store;

import com.capstone.petgroomingapplication.model.UserModel;
import com.google.firebase.Timestamp;

public class LocalUser {
    private static String phoneNumber;
    private static String username;
    private static Timestamp registrationDate;
    private static String userId;
    private static String password;
    private static String contactNo;
    private static String address;
    private static String profilePicUrl;
    private static String collection; // Ad

    public static void Store (UserModel user) {
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        registrationDate = user.getRegistrationDate();
        userId = user.getUserId();
        password = user.getPassword();
        contactNo = user.getContactNo();
        address = user.getAddress();
        profilePicUrl = user.getProfilePicUrl();
        collection = user.getCollection();
    }

    public static String getCollection() { return collection; }
    public static void setCollection(String collection) { collection = collection; }

    public static String getPhoneNumber() { return phoneNumber; }
    public static void setPhoneNumber(String phoneNumber) { phoneNumber = phoneNumber; }

    public static String getUsername() { return username; }
    public static void setUsername(String username) { username = username; }

    public static Timestamp getRegistrationDate() { return registrationDate; }
    public static void setRegistrationDate(Timestamp registrationDate) { registrationDate = registrationDate; }

    public static String getUserId() { return userId; }
    public static void setUserId(String userId) { userId = userId; }

    public static String getPassword() { return password; }
    public static void setPassword(String password) { password = password; }

    public static String getContactNo() { return contactNo; }
    public static void setContactNo(String contactNo) { contactNo = contactNo; }

    public static String getAddress() { return address; }
    public static void setAddress(String address) { address = address; }

    public static String getProfilePicUrl() { return profilePicUrl; } // Getter for profile picture URL
    public static void setProfilePicUrl(String profilePicUrl) { profilePicUrl = profilePicUrl; } // S
}
