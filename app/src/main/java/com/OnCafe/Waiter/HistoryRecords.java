package com.OnCafe.Waiter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.OnCafe.Management.CategorySelectedDBAdapter;
import com.OnCafe.R;

/**
 * Created by Bishal on 8/23/2015.
 */
public class HistoryRecords extends ActionBarActivity {
RecordDBAdapter record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments);
        record=new RecordDBAdapter(HistoryRecords.this);
        printDatabase();

    }
    public void printDatabase() {

        Cursor cursor = record.getAllRows();
        final String[] fromFieldNames = new String[]{
                RecordDBAdapter.COLUMN_TIME, RecordDBAdapter.COLUMN_GRANDTOTAL};
        int[] toViewIDs = new int[]{R.id.waiterSelectedName,R.id.waiterSelectedPrice};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(HistoryRecords.this, R.layout.waiter_food_menu_list_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.recordListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
               String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                // int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[2]));





                //make a Toast
                Toast.makeText(HistoryRecords.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(HistoryRecords.this,new_trial.class);
        finish();
        startActivity(i);
    }
}
