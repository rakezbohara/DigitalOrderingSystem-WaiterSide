package com.OnCafe.Management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.OnCafe.ExitDialog;
import com.OnCafe.R;
import com.OnCafe.Waiter.Tables;
import com.OnCafe.Waiter.WaiterHome;

/**
 * Created by Bishal on 8/9/2015.
 */
public class LoginPage extends ActionBarActivity {
    EditText pass;
    String newPassword;
    String inPassword;
    SharedPreferences sPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        sPassword= getSharedPreferences("NewPassword",0);

        newPassword= sPassword.getString("hello","admin");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#72231F")));

    }


   public void waiterClicked(View view){
        Intent i= new Intent(LoginPage.this,WaiterHome.class);
        finish();
        startActivity(i);


    }

    @Override
    public void onBackPressed() {

        ExitDialog e= new ExitDialog(LoginPage.this);
        e.show();

    }

    public void adminClicked(View view){

        pass=(EditText)findViewById(R.id.pass);
        inPassword= pass.getText().toString();
        if(inPassword.equals(newPassword))
        {

          Intent intent= new Intent(LoginPage.this,ManagerActivity.class);
          finish();
          startActivity(intent);
        }

        else{ Toast.makeText(LoginPage.this,
                "Incorrect Password",
                Toast.LENGTH_LONG).show();
            pass.setText("");
        }



    }


}
