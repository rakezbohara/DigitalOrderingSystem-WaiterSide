package com.OnCafe.Waiter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.R;


public class BillFragment extends Fragment implements orderTabs.YourFragmentInterface {
    EditText bishalsInput;
OrderedDBAdapter odbAdapter;
    SharedPreferences sDiscount;
    int gTotal;
    TextView dAmt;
    String discount;
    TextView dPercent;
TextView btnPay;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.bill_fragment, container, false);
        btnPay= (Button)view.findViewById(R.id.btnPay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    PaymentDialog p = new PaymentDialog(view.getContext(), gTotal);
                    p.show();

            }
        });
        SharedPreferences sTableNo = this.getActivity().getSharedPreferences("discount", Context.MODE_PRIVATE);
        discount= sTableNo.getString("discount","5");
        odbAdapter=new OrderedDBAdapter(view.getContext());
        printDatabase(view);
        return view;

    }
    @Override
    public void fragmentBecameVisible() {
        // You can do your animation here because we are visible! (make sure onViewCreated has been called too and the Layout has been laid. Source for another question but you get the idea.


        View v= getView();
        printDatabase(v);
    }

    public void printDatabase(final View view) {


        Cursor cursor = odbAdapter.getAllRows();
        final String[] fromFieldNames = new String[]{

                OrderedDBAdapter.COLUMN_PRODUCTNAME,
                OrderedDBAdapter.COLUMN_AMOUNT,

                OrderedDBAdapter.COLUMN_COST};
        int[] toViewIDs = new int[]{

                R.id.billItem,
                R.id.billAmt,
                R.id.billCost
        };
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(view.getContext(), R.layout.bill_row, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) view.findViewById(R.id.billList);
        myList.setAdapter(myCursorAdapter);

        odbAdapter= new OrderedDBAdapter(view.getContext());
        Cursor cur =odbAdapter.get_sum();
        int amount=0;
        if(cur.moveToFirst())
        {
            amount = cur.getInt(0);
        }

        TextView subTotal= (TextView)view.findViewById(R.id.subTotal);
        subTotal.setText("" + amount);
        TextView grandTotal=(TextView)view.findViewById(R.id.grandTotal);
        dAmt=(TextView)view.findViewById(R.id.dAmt);


        dPercent=(TextView)view.findViewById(R.id.dPercent);
        dPercent.setText(discount+"%");


        int discountAmt=Integer.parseInt(discount)*amount/100;
        gTotal= amount-discountAmt;
        dAmt.setText("" + discountAmt);
        grandTotal.setText("" + gTotal);

    }




    }



