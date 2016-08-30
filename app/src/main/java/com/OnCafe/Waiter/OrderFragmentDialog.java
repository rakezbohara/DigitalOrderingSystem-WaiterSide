package com.OnCafe.Waiter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.OnCafe.R;

import static com.OnCafe.Waiter.orderTabs.*;

/**
 * Created by Bishal on 8/17/2015.
 */
public class OrderFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Context c;
    public Dialog d;


    int amount;
    String name;

    int selPrice;
    int sID;
    OrderedDBAdapter odbAdapter;

    TextView orderNum;
    int calcAmt;
    int perPrice;


    public OrderFragmentDialog(Context a, int sID, String FoodName,int amount,int calcAmt,int perPrice, int selectedPrice) {
        super(a);

        this.c = a;
        this.sID = sID;
        this.name = FoodName;
        this.amount=amount;

        this.calcAmt=calcAmt;
        this.perPrice=perPrice;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_order_fragment);

        setTitle(name);

        orderNum=(TextView)findViewById(R.id.orderNum);
        orderNum.setText("("+amount+")");
        Button orderBtnOk=(Button)findViewById(R.id.orderBtnOk);
        Button orderBtnDel=(Button)findViewById(R.id.orderBtnDel);
        Button orderBtnPlus=(Button)findViewById(R.id.orderPlus);
        Button orderBtnMinus=(Button)findViewById(R.id.orderMinus);


        orderBtnOk.setOnClickListener(this);
        orderBtnDel.setOnClickListener(this);
        orderBtnPlus.setOnClickListener(this);
        orderBtnMinus.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderPlus:
                amount++;
                orderNum.setText("("+amount+")");


                break;
            case R.id.orderMinus:
                amount--;
                orderNum.setText("("+amount+")");


                break;

            case R.id.orderBtnOk:




                odbAdapter= new OrderedDBAdapter(getContext());
                odbAdapter.updateRecord(sID,amount, perPrice);
                dismiss();




                break;
            case R.id.orderBtnDel:
                odbAdapter= new OrderedDBAdapter(getContext());
                odbAdapter.deleteProduct(sID);
                dismiss();
               break;





            default:
                break;
        }

    }


}






