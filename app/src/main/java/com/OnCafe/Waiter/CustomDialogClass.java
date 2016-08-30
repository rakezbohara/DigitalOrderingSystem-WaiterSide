package com.OnCafe.Waiter;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.OnCafe.R;

/**
 * Created by Bishal on 8/17/2015.
 */
public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

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




    public CustomDialogClass(Activity a,String FoodName,int selectedPrice) {
        super(a);

        this.c = a;
        this.name=FoodName;
        this.perPrice=selectedPrice;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_numpad);
        setTitle(name);


        amt= (EditText) findViewById(R.id.amt);


        amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });


        Button btnok = (Button) findViewById(R.id.btnok);



        btnok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {




            case R.id.btnok:
                if(!amt.getText().toString().equals("")) {
                    amount = Integer.parseInt(amt.getText().toString());

                    odbAdapter = new OrderedDBAdapter(getContext());

                    odbAdapter.addProduct(amount, name, perPrice);
                }


                dismiss();




                break;

            default:
                break;
        }

    }

}