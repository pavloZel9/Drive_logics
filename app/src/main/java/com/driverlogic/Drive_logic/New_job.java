package com.driverlogic.Drive_logic;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class New_job {
    public String User_id;
    public String name_job;
    public String full_text_job;
    public String Date_great;
    public String Date_end;



    public New_job() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public New_job(String User_id,String name_job, String full_text_job,String Date_great,String Date_end) {
        this.User_id = User_id;
        this.name_job = name_job;
        this.full_text_job = full_text_job;
        this.Date_great = Date_great;
        this.Date_end = Date_end;

    }


    public String getName(){
        return User_id;
    }
    public String getName_job(){
        return name_job;
    }
    public String get_Date_great(){
        return Date_great;
    }
  /*  public String getHave(){
        return have;
    }*/

}