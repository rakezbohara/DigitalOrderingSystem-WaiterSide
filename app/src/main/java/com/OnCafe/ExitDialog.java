package com.OnCafe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.OnCafe.Management.CategorySelectedDBAdapter;
import com.OnCafe.Management.ManagerFoodMenu;

/**
 * Created by Bishal on 8/17/2015.
 */
public class ExitDialog extends Dialog implements
        View.OnClickListener {

    public Context c;





    public ExitDialog(Context a) {
        super(a);

        this.c = a;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_exit);


        Button exitBtnYes = (Button) findViewById(R.id.exitBtnYes);
        Button exitBtnNo = (Button) findViewById(R.id.exitBtnNo);

        exitBtnYes.setOnClickListener(this);
        exitBtnNo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitBtnNo:


                dismiss();

                break;

            case R.id.exitBtnYes:

             System.exit(0);


                break;

            default:
                break;
        }

    }
}






