package com.OnCafe.Management;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.OnCafe.R;

/**
 * Created by Bishal on 8/17/2015.
 */
public class ProductClickedDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;


    EditText amt;
    String name;
    EditText editProductName;
    EditText editPrice;
    int selPrice;
    String sID;
    ManagerFoodMenu mfm;

    CategorySelectedDBAdapter csdb;


    public ProductClickedDialog(Activity a, String FoodName, int selectedPrice, String sID) {
        super(a);

        this.c = a;
        this.name = FoodName;
        this.selPrice = selectedPrice;
        this.sID = sID;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_product_clicked);
        setTitle(name);

        TextView editBtn=(TextView)findViewById(R.id.editBtn);
        TextView delBtn=(TextView)findViewById(R.id.delBtn);
        editBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editBtn:
                editProductDialog cdd = new editProductDialog( getContext(), name, selPrice, sID);
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        dismiss();
                    }
                });




                break;
            case R.id.delBtn:
                deleteProductDialog dpd = new deleteProductDialog( getContext(), name, selPrice, sID);
                dpd.show();
                dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        dismiss();
                    }
                });

               break;





            default:
                break;
        }

    }
}






