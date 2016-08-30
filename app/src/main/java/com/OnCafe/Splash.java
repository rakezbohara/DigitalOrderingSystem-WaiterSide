package com.OnCafe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.OnCafe.Management.LoginPage;

/**
 * Created by Bishal on 8/15/2015.
 */
public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //getSupportActionBar().collapseActionView();
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("00000")));
       // getSupportActionBar().setTitle("Welcome");
        Thread timer= new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Intent i = new Intent(Splash.this, LoginPage.class);
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}
