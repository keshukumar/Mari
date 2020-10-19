package com.example.marikiti.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_LOGIN_GUIDE = 101;
    private Context mContext;
    private CountDownTimer downTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        this.mContext = this;
        countDownTimer();
        generateHashKey();
        initView();
    }

    private void initView() {

    }

    private void countDownTimer() {

        downTimer = new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                /*if (AppPrefs.getBoolean(App.Key.IS_LOGGED)) {
                    Log.d(TAG, "onFinish: *****Continue with USER Login Id - " + AppPrefs.getString(App.Key.ID_LOGGED) + " *************");
                    Intent intent = new Intent(SplashActivityDemo.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    if(AppPrefs.getBoolean(App.Key.IS_WELCOMED)){
                        Intent intent = new Intent(SplashActivityDemo.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SplashActivityDemo.this, WelcomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }*/
            }
        };
        downTimer.start();
    }

    private void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("cubewires.sudzero", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}

// SR617973504