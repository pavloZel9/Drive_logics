package com.driverlogic.Drive_logic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    Activity activity;
    List<New_job> listUsers;
    LayoutInflater inflater;

    public ListViewAdapter(Activity activity, List<New_job> listUsers) {
        this.activity = activity;
        this.listUsers = listUsers;
    }

    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return listUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) activity
                .getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item, null);

        TextView txtUser = (TextView) itemView.findViewById(R.id.item_list_name);
        TextView txtEmail = (TextView) itemView.findViewById(R.id.item_list_name_job);
        TextView t2 = (TextView) itemView.findViewById(R.id.item_list_date);

       txtEmail.setText(listUsers.get(i).getName());
        txtUser.setText(listUsers.get(i).getName_job());
        t2.setText(listUsers.get(i).get_Date_great());
        return  itemView;
    }
}
