package com.OnCafe.Waiter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.OnCafe.Management.LoginPage;
import com.OnCafe.Management.ManagerActivity;
import com.OnCafe.R;

/**
 * Created by Bishal on 8/17/2015.
 */
public class Goadmin extends Dialog {

    public Activity c;
    public Dialog d;
    public Button yes, no,ok;
    public int amount;
    String num="";
    EditText amt;
    String name;
    String totalAmt;
    int perPrice;
    OrderedDBAdapter odbAdapter;

    EditText pass;
    String newPassword;
    String inPassword;
    SharedPreferences sPassword;





    public Goadmin(Activity a) {
        super(a);

        this.c = a;

        sPassword= a.getSharedPreferences("NewPassword", 0);

        newPassword= sPassword.getString("hello","admin");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.dialog_login);
      //  getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        pass=(EditText)findViewById(R.id.passkey);

       pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });




        Button btnok = (Button) findViewById(R.id.btok);



        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inPassword= pass.getText().toString();
                if(inPassword.equals(newPassword))
                {

                    Intent intent= new Intent(c,ManagerActivity.class);
                    c.finish();
                    c.startActivity(intent);
                }

                else{ Toast.makeText(c,
                        "Incorrect Password",
                        Toast.LENGTH_LONG).show();
                    pass.setText("");
                }



                dismiss();
            }
        });

    }



}