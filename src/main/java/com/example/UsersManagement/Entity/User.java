package com.example.UsersManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="address")
    private String Address;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User() {
    }

    public User(int id, String firstName, String secondName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
        Address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return lastName;
    }

    public void setSecondName(String secondName) {
        this.lastName = secondName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + lastName + '\'' +
                ", Address='" + Address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}