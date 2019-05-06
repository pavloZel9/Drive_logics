package com.driverlogic.Drive_logic;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;


import org.apache.commons.io.FileUtils;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;



public class Reg_layout2 extends Fragment {
    final String LOG_TAG = "myLogs";
    String id,name,email,pic;
    TextView tt1,tt2;
    ImageView v1,v2;
    SharedPreferences sPref;
    Button b1,b2,b3;
    EditText t1,t2;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    String code="";
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reg_layout2, null);



        mAuth = FirebaseAuth.getInstance();

        sPref =this.getActivity().getPreferences(MODE_PRIVATE);
        name = sPref.getString("name", "");
        pic=sPref.getString("icons", "");
        email=sPref.getString("email", "");
        v1=v.findViewById(R.id.img_frag2);
        v2=v.findViewById(R.id.img_foto);
        tt1=v.findViewById(R.id.frag2_name);
        tt1.setText(name);
        tt2=v.findViewById(R.id.frag2_email);
        tt2.setText(email);
        Picasso.with(getActivity())
                .load(pic)
                .placeholder(R.drawable.com_facebook_button_icon)
                .error(R.drawable.com_facebook_auth_dialog_cancel_background)
                .into(v1);
        b1 =v.findViewById(R.id.frag2_get_reg_num_code);
        b2 =v.findViewById(R.id.frag2_set_reg_num_code);
        b3=v.findViewById(R.id.frag2_foto);
        t1=v.findViewById(R.id.frag2_set_reg_num_code_input);
        t2=v.findViewById(R.id.frag2_set_reg_num_code_input);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                sendVerificationCode(t1.getText().toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(code.equals(t2.getText().toString())){
                    Log.e("True","True");
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFileChooser();
            }
        });
        return v;

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    private void sendVerificationCode(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+380507136542" ,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            code = phoneAuthCredential.getSmsCode();
            Log.e("sendCode","True");
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            Log.e("1",code);


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("Failed","True");
        }




        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.e("sendCode","пошло");

        }
    };
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    0);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e("1", "File Uri: " + uri.toString());
                    // Get the path

                    v2.setImageURI(uri);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
