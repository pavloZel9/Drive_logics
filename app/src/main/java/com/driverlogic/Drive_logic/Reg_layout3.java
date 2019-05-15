package com.driverlogic.Drive_logic;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class Reg_layout3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private DatabaseReference myRootRef;
    TextView t1,t2,t3;
    SharedPreferences sPref;
    String id;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reg_layout3, null);
        Button b1=v.findViewById(R.id.frag3_great_job);
        t1=v.findViewById(R.id.frag3_name_job);
        t2=v.findViewById(R.id.frag3_full_text_job);
        t3=v.findViewById(R.id.frag3_Date_end);
        sPref =this.getActivity().getPreferences(MODE_PRIVATE);
        id=sPref.getString("id", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        myRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://worker-1990.firebaseio.com/");
        final String currentDateandTime = sdf.format(new Date());
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeNewJob(id,t1.getText().toString(),t2.getText().toString(),currentDateandTime,t3.getText().toString());
            }
        });
        return v;
    }
    private void writeNewJob(String User_id,String name_job, String full_text_job, String Date_great,String Date_and) {
        New_job user = new New_job(User_id,name_job, full_text_job,Date_great,Date_and);

        myRootRef.child("jobs").child(User_id).child(Date_great).setValue(user);
    }
}