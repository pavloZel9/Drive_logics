package com.driverlogic.Drive_logic;

import android.Manifest;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    boolean isLoggedIn;

    @Override
    /////////////////////////////////////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
          ///
          // Callback registration

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        Log.e("per", Boolean.toString(isLoggedIn));
        if (isLoggedIn == true) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        } else {
            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code

                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancel() {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Отменено!", Toast.LENGTH_SHORT);
                            toast.show();
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Ошибка!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
        }
    }
    ///////////////////////////////////////////////////
    //Permissio
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
    //Permission
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
