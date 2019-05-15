package com.driverlogic.Drive_logic;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sPref;
    String id,name,email,pic;
    String open_add_win="0";
    AccessToken mAccessToken;
    ImageView icon_myinfo;
    FragmentTransaction fTrans,fTrans3;
    Reg_layout3 frag3;
    private DatabaseReference myRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccessToken = AccessToken.getCurrentAccessToken();
        getUserProfile(mAccessToken);
        setContentView(R.layout.activity_main2);
        myRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://worker-1990.firebaseio.com/");
        Toolbar toolbar = findViewById(R.id.toolbar);




        DatabaseReference ref1 = myRootRef.child("users").child("2342123355835453");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reg_User user = dataSnapshot.getValue(Reg_User.class);
                // ...
                try {
                    Log.e("YES1", user.getHave());
                    open_add_win=user.getHave();
                }catch(Exception e) {
                    open_add_win="0";
                }
                Log.e("open_add_win", open_add_win);
                if(open_add_win.equals("0")) {
                    Reg_layout1 frag1 = new Reg_layout1();
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.add(R.id.frgmCont, frag1);
                    fTrans.commit();

                }
                if(open_add_win.equals("1")) {
                    frag3=new Reg_layout3();
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.add(R.id.frgmCont,frag3);
                    fTrans.commit();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        icon_myinfo= findViewById(R.id.icon_myinfo);


        Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
        Picasso.with(Main2Activity.this)
                .load(pic)
                .placeholder(R.drawable.com_facebook_button_icon)
                .error(R.drawable.com_facebook_auth_dialog_cancel_background)
                .into(icon_myinfo);
        TextView t1=findViewById(R.id.name_myinfo);
        t1.setText(name);
        TextView t2=findViewById(R.id.email_myinfo);
        t2.setText(email);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            List_jobs frag7=new List_jobs();
            FragmentTransaction fTrans13 = getFragmentManager().beginTransaction();
            fTrans13.replace(R.id.frgmCont, frag7);
            fTrans13.commit();
        } else if (id == R.id.nav_gallery) {
            Reg_layout1 frag2=new Reg_layout1();
            FragmentTransaction fTrans1 = getFragmentManager().beginTransaction();
            fTrans1.replace(R.id.frgmCont, frag2);
            fTrans1.commit();

        }  else if (id == R.id.nav_share) {


            Reg_layout2 frag3=new Reg_layout2();
            FragmentTransaction  fTrans2 = getFragmentManager().beginTransaction();
            fTrans2.replace(R.id.frgmCont, frag3);
            fTrans2.commit();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //You can fetch user info like this…
                            pic=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            name=object.getString("name");
                            email=object.getString("email");
                            id=object.getString("id");
                            saveText();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("icons", pic);
        ed.putString("name", name);
        ed.putString("email", email);
        ed.putString("id", id);

        ed.commit();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }
}
