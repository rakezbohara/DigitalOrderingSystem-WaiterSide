package com.OnCafe.Waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.OnCafe.R;

/**
 * Created by Bishal on 7/26/2015.
 */
public class CustomTableAdapter extends ArrayAdapter<String>{

    OrderedDBAdapter odb;

    public CustomTableAdapter(Context context, String[] tableNumber) {
        super(context, R.layout.custom_table_layout ,tableNumber);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater bishalsInflater= LayoutInflater.from(getContext());
View customView=bishalsInflater.inflate(R.layout.custom_table_layout,parent,false);


        String tableNum = getItem(position);
        //String tablesN=String.valueOf(getItem(position));
        TextView bishalsText= (TextView) customView.findViewById(R.id.bishalsText);
        ImageView bishalsImage= (ImageView) customView.findViewById(R.id.homeImage);

        bishalsText.setText(tableNum);
        //bishalsImage.setImageResource(R.drawable.table_default);



        //table existence check

        odb = new OrderedDBAdapter(getContext());
        OrderTable tableNo=new OrderTable("",tableNum);


        if( odb.TableExist(tableNo))
        {

            bishalsImage.setImageResource(R.drawable.table_contained);
        }
        else {
             bishalsImage.setImageResource(R.drawable.table_default);

        }


        return customView;


    }
}

