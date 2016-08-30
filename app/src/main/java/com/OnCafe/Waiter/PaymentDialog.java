package com.OnCafe.Waiter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.OnCafe.R;

/**
 * Created by Bishal on 9/6/2015.
 */
public class PaymentDialog extends Dialog implements View.OnClickListener  {
    String discount;
    EditText eDiscount;
    OrderedDBAdapter odb;
    RecordDBAdapter record;
    int gtotal;

    public PaymentDialog(Context context,int total) {
        super(context);
        this.gtotal= total;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_dialog);
        setTitle("Payment done?");
        Button bDone=(Button)findViewById(R.id.bDone);
        bDone.setOnClickListener(this);

        Button bNot=(Button)findViewById(R.id.bNot);
        bNot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bDone:

            record= new RecordDBAdapter(getContext());

                SharedPreferences sTableNo = this.getContext().getSharedPreferences("Table Number", Context.MODE_PRIVATE);

               String tableN= sTableNo.getString("hello","Table");
                SharedPreferences sTime = this.getContext().getSharedPreferences("Time"+tableN, Context.MODE_PRIVATE);
               String time= sTime.getString("Time","Nothing to show");
             int total=gtotal;

                record.save(time,total);
                odb= new OrderedDBAdapter(getContext());
                odb.clear();
               TableNoFrontDB frontDB=new TableNoFrontDB(getContext());
                frontDB.deleteProduct(tableN);
                Toast.makeText(getContext(),"Payment Success, saved to Record",Toast.LENGTH_LONG).show();
                dismiss();
                break;
            case R.id.bNot:

                dismiss();

                break;

            default:
                break;
        }
    }


}
