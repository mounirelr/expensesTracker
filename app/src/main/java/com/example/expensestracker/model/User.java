package com.example.expensestracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.intellij.lang.annotations.Identifier;

public class User implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String currency;
    public User(){

    }
    public User(String firstName,String lastName,String email , String password , String currency){

        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.currency=currency;

    }

    public User(Integer id ,String firstName,String lastName,String email  , String currency){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;

        this.currency=currency;

    }

    protected User(Parcel in) {
        id= Integer.parseInt(in.readString());
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        currency = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(String.valueOf(id));
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(currency);
    }
}
