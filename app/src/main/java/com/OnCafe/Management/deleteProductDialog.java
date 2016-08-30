package com.OnCafe.Management;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.OnCafe.R;

/**
 * Created by Bishal on 8/17/2015.
 */
public class deleteProductDialog extends Dialog implements
        View.OnClickListener {

    public Context c;
    public Dialog d;


    EditText amt;
    String name;
    EditText editProductName;
    EditText editPrice;
    int selPrice;
    int sID;
    ManagerFoodMenu mfm;

    CategorySelectedDBAdapter csdb;



    public deleteProductDialog(Context a, String FoodName, int selectedPrice, String sID) {
        super(a);

        this.c = a;
        this.name = FoodName;
        this.selPrice = selectedPrice;
        this.sID = Integer.parseInt(sID);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_product);

        setTitle("Confirm delete " + name+"?");


        Button btnYes = (Button) findViewById(R.id.btnYes);
        Button btnNo = (Button) findViewById(R.id.btnNo);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                csdb = new CategorySelectedDBAdapter(getContext());
                csdb.deleteProduct(sID);
                dismiss();



                break;
            case R.id.btnNo:
                dismiss();



                break;

            default:
                break;
        }

    }
}






