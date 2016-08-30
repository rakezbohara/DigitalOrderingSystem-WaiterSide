package com.OnCafe.Management;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.OnCafe.R;

/**
 * Created by Bishal on 9/6/2015.
 */
public class Discount extends android.app.Dialog implements View.OnClickListener  {
    String discount;
    EditText eDiscount;

    public Discount(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount);
        Button dOkBtn=(Button)findViewById(R.id.dOkBtn);
        dOkBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dOkBtn:

                eDiscount= (EditText)findViewById(R.id.eDiscount);
                discount= eDiscount.getText().toString();
                if (discount==null){
                    discount="0";
                   Toast.makeText(getContext(),"discount= "+discount+"% saved",Toast.LENGTH_LONG).show();
                }
                SharedPreferences preferences = this.getContext().getSharedPreferences("discount", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();

                editor.putString("discount",discount);
                editor.apply();
                Toast.makeText(getContext(),"discount= "+discount+"% saved",Toast.LENGTH_LONG).show();
                dismiss();
                break;
            default:
                break;
        }
    }


}
