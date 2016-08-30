package com.OnCafe.Waiter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.OnCafe.R;

import java.util.List;

/**
 * Created by Bishal on 7/26/2015.
 */
public class CustomTestFrontTableAdapter extends ArrayAdapter<String>{

    OrderedDBAdapter odb;

    public CustomTestFrontTableAdapter(Context context, String[] tableNumber) {
        super(context, R.layout.test_front_order_layout ,tableNumber);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater bishalsInflater= LayoutInflater.from(getContext());
View customView=bishalsInflater.inflate(R.layout.test_front_order_layout,parent,false);


        String tableNum = getItem(position);
        odb=new OrderedDBAdapter(getContext());
        OrderTable tableNo= new OrderTable(tableNum);

        odb.setName(tableNo);

        //String tablesN=String.valueOf(getItem(position));

        TextView tableNumText=(TextView) customView.findViewById(R.id.testTableNum);
        tableNumText.setText("Table no"+tableNum);






        //bishalsImage.setImageResource(R.drawable.table_default);



        //table existence check



        Cursor cursor = odb.getAllRows();
        final String[] fromFieldNames = new String[]{
                OrderedDBAdapter.COLUMN_AMOUNT,
                OrderedDBAdapter.COLUMN_PRODUCTNAME
        };

            int[] toViewIDs = new int[]{

                    R.id.testAmt,
                    R.id.testItem

                    // R.id.testAmt,
                    //R.id.testItem
            };

            final SimpleCursorAdapter myCursorAdapter;
            myCursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.custom_test_front_order_layout, cursor, fromFieldNames, toViewIDs, 0);

            ListView myList = (ListView) customView.findViewById(R.id.testListView);
            myList.setAdapter(myCursorAdapter);
        setListViewHeightBasedOnChildren(myList);

        return customView;


    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        setListViewHeight(listView, totalHeight);
    }

    public static void setListViewHeight(ListView listView, int height) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}

