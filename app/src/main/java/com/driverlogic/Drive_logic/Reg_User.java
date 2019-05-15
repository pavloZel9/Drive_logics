package com.driverlogic.Drive_logic;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Reg_User {

    public String id_facebook;
    public String name;
    public String location;
    public String number;
    public String have;


    public Reg_User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Reg_User(String id_facebook, String name,String location,String number,String have) {
        this.id_facebook = id_facebook;
        this.name = name;
        this.location = location;
        this.number = number;
        this.have=have;
    }
    public String get_id(){
        return id_facebook;
    }
    public String getHave(){
        return have;
    }

}