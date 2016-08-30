package com.OnCafe.Management;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.OnCafe.ExitDialog;
import com.OnCafe.R;
import com.OnCafe.Waiter.new_trial;

/**
 * Created by Bishal on 8/8/2015.
 */
public class ManagerActivity extends ActionBarActivity {

    EditText changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);
        getSupportActionBar().setTitle("Administrator");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));



    }


public  void editMenuClicked(View view){

    Intent i= new Intent(ManagerActivity.this,ManagerCategory.class);
    finish();
    startActivity(i);
}

    public void changePassword(View view){
        Intent j= new Intent(ManagerActivity.this,ChangePassword.class);
        finish();
        startActivity(j);
    }
    public void signOut(View view){
        Intent k= new Intent(ManagerActivity.this,new_trial.class);
        finish();
        startActivity(k);
    }
    public void setDiscount(View view){
        Discount d= new Discount(ManagerActivity.this);
        d.show();
    }

    @Override
    public void onBackPressed() {

        ExitDialog e= new ExitDialog(ManagerActivity.this);
        e.show();

    }












}
