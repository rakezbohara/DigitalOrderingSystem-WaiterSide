package com.OnCafe.Waiter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.OnCafe.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bishal on 8/26/2015.
 */
public class TestFrontOrders extends ActionBarActivity {
    TableNoFrontDB frontDB;
    OrderedDBAdapter odbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        odbAdapter = new OrderedDBAdapter(this);
        ArrayList<String> stringArrayList = new ArrayList<String>();
        String[] tableNum = stringArrayList.toArray(new String[stringArrayList.size()]);
        frontDB=new TableNoFrontDB(TestFrontOrders.this);
        for(int i=1;i<=15;i++)
        {

            if(odbAdapter.frontTableExist(String.valueOf(i))&&!Arrays.asList(tableNum).contains(String.valueOf(i)) )//&& !Arrays.asList(temp).contains(String.valueOf(i))) {

            {
                stringArrayList.add(String.valueOf(i));
                tableNum = stringArrayList.toArray(new String[stringArrayList.size()]);
            }


        }
        ListAdapter bishalsAdapter = new CustomTestFrontTableAdapter(this,tableNum);
        ListView bishalsListView = (ListView) findViewById(R.id.testFrontList);
        bishalsListView.setAdapter(bishalsAdapter);
        setListViewHeightBasedOnChildren(bishalsListView);


        //printTables();
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


    /*
    public void printTables(){
       Cursor cursor= frontDB.getAllRows();
        final String[] fromFieldNames = new String[]{
                TableNoFrontDB.COLUMN_TABLENUM
        };
        int[] toViewIDs = new int[]{

                R.id.homeText
        };
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(TestFrontOrders.this, R.layout.home_custom_table_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.waiterHomeListView);
        myList.setAdapter(myCursorAdapter);

    }*/

}
