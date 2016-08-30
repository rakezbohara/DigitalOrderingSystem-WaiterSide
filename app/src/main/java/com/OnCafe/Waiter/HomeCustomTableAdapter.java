package com.OnCafe.Waiter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.OnCafe.R;

/**
 * Created by Bishal on 7/26/2015.
 */
public class HomeCustomTableAdapter extends ArrayAdapter<String> {

    OrderedDBAdapter odbAdapter;
    String total;
    String tableNum;
    String cost;
    public HomeCustomTableAdapter(Context context, String[] tableNumber) {
        super(context, R.layout.home_custom_table_layout, tableNumber);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater bishalsInflater = LayoutInflater.from(getContext());
        View customView = bishalsInflater.inflate(R.layout.home_custom_table_layout, parent, false);


         tableNum = getItem(position);
        //String tablesN=String.valueOf(getItem(position));
        TextView bishalsText = (TextView) customView.findViewById(R.id.homeText);


        bishalsText.setText("Table No "+tableNum);
        TableNoFrontDB tableNoToAdd=new TableNoFrontDB(getContext());
        tableNoToAdd.addProduct(tableNum);

        odbAdapter = new OrderedDBAdapter(getContext());
        OrderTable tableNo = new OrderTable(tableNum);
        odbAdapter.setName(tableNo);



/*        Cursor cur =odbAdapter.get_total(tableNum);
        int amount=0;
        if(cur.moveToFirst())
        {
            amount = cur.getInt(0);
        }

        totalCost.setText("" + amount);

        bishalsText.setText(tableNum);
        bishalsImage.setImageResource(R.drawable.table_contained);




*/



        odbAdapter = new OrderedDBAdapter(getContext());
        Cursor cur = odbAdapter.get_sum();
        int amount = 0;
        if (cur.moveToFirst()) {
            amount = cur.getInt(0);
        }

       // TextView total = (TextView) customView.findViewById(R.id.frontTotalCost);
        cost=String.valueOf(amount);
       // total.setText(cost);


        return customView;


    }


}

