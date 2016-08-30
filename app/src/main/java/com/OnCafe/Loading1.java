package com.OnCafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.OnCafe.Management.LoginPage;
import com.OnCafe.Waiter.WaiterHome;
import com.OnCafe.Waiter.new_trial;

/**
 * Created by Bishal on 8/15/2015.
 */
public class Loading1 extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen1);
        imageView=(ImageView)findViewById(R.id.waiter_splash);

       /*int fadeInDuration = 2300; // Configure time values here

        imageView.setVisibility(View.INVISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        imageView.setAnimation(animation);*/

        Thread timer= new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Intent i = new Intent(Loading1.this,new_trial.class);
                    finish();
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}
