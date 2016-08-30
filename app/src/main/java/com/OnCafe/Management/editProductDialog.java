package com.OnCafe.Management;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.OnCafe.R;

/**
 * Created by Bishal on 8/17/2015.
 */
public class editProductDialog extends Dialog implements
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



    public editProductDialog(Context a, String FoodName, int selectedPrice, String sID) {
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
        setContentView(R.layout.dialog_edit_product);
        editProductName = (EditText) findViewById(R.id.editProductName);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editProductName.setText(name);
        editPrice.setText(String.valueOf(selPrice));


        Button btnSave = (Button) findViewById(R.id.editSave);

        btnSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editSave:
                String newProductName = editProductName.getText().toString();
                String nPrice = editPrice.getText().toString();
                editProductName.setText("");
                editPrice.setText("");
                int newPrice = Integer.parseInt(nPrice);
                csdb = new CategorySelectedDBAdapter(getContext());
                csdb.updateRecord(newProductName, newPrice, sID);
                dismiss();



                break;


            default:
                break;
        }

    }
}






