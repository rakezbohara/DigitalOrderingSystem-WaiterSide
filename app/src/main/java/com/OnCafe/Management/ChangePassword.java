package com.OnCafe.Management;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.R;
import com.OnCafe.Waiter.WaiterHome;

/**
 * Created by Bishal on 8/9/2015.
 */
public class ChangePassword extends Activity {
    EditText newPass,confirmPass;
    Button save;
    static  String fileName= "NewPassword";
    SharedPreferences sPassword;
    String newPassword;
    String confirmPass1;
    TextView newPassText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);


        newPass=(EditText)findViewById(R.id.newPass);
        confirmPass=(EditText)findViewById(R.id.confirmPass);
        save=(Button)findViewById(R.id.save);
        sPassword= getSharedPreferences(fileName,0);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword=newPass.getText().toString();
                confirmPass1=confirmPass.getText().toString();
                if(newPassword.equals(confirmPass1))
                { sPassword= getSharedPreferences(fileName,0);

                    SharedPreferences.Editor editor=sPassword.edit();
                    editor.putString("hello",newPassword);
                    editor.apply();
                    sPassword= getSharedPreferences(fileName,0);
                    Toast.makeText(ChangePassword.this,
                            "Password Changed",
                            Toast.LENGTH_LONG).show();
                newPass.setText("");
                confirmPass.setText("");}
                else{
                    newPass.setText("");
                    confirmPass.setText("");
                    Toast.makeText(ChangePassword.this,
                            "Password does not match",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChangePassword.this,ManagerActivity.class);
        finish();
        startActivity(i);
    }

}
