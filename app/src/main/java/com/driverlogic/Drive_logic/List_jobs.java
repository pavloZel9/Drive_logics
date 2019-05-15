package com.driverlogic.Drive_logic;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class List_jobs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private List<New_job> list_users = new ArrayList<>();
    ListView list_data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_jobs, null);
        list_data = (ListView) v.findViewById(R.id.listview);
        initFirebase();
        addEventFirebaseListener();

        return v;
    }

    private void initFirebase() {
        //инициализируем наше приложение для Firebase согласно параметрам в google-services.json
        // (google-services.json - файл, с настройками для firebase, кот. мы получили во время регистрации)
        FirebaseApp.initializeApp(getActivity());
        //получаем точку входа для базы данных
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //получаем ссылку для работы с базой данных
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://worker-1990.firebaseio.com/");
    }

    private void addEventFirebaseListener() {
        //показываем View загрузки


        mDatabaseReference.child("jobs")
                .addValueEventListener(new ValueEventListener() {
                    //если данные в БД меняются
                    int i=0;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            if (list_users.size() > 0) {
                                list_users.clear();
                            }
                            //проходим по всем записям и помещаем их в list_users в виде класса User
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                for (DataSnapshot ds1 : postSnapshot.getChildren()) {
                                    New_job user = ds1.getValue(New_job.class);
                                    list_users.add(user);



                                }
                            }
                            //публикуем данные в ListView
                            ListViewAdapter adapter = new ListViewAdapter(getActivity(), list_users);
                            list_data.setAdapter(adapter);
                        }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}