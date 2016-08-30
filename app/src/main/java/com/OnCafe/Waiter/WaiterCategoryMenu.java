package com.OnCafe.Waiter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.OnCafe.Management.CategoryDBAdapter;
import com.OnCafe.Management.CategorySelectedDBAdapter;
import com.OnCafe.Management.DatabaseName;
import com.OnCafe.R;

/**
 * Created by Bishal on 8/13/2015.
 */
public class WaiterCategoryMenu extends AppCompatActivity {

    String tableNo;
    String newtno;
    SharedPreferences sTableNo;
CategorySelectedDBAdapter csdb;
    CategoryDBAdapter cdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_category_menu);

        sTableNo= getSharedPreferences("Table Number",0);
        tableNo= sTableNo.getString("hello", "Table");
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#72231F")));
    getSupportActionBar().setTitle("Table " + tableNo + "  Categories");
    cdb=new CategoryDBAdapter(WaiterCategoryMenu.this);
    printDatabase();
        FloatingActionButton home=(FloatingActionButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(WaiterCategoryMenu.this, new_trial.class));

            }
        });

}


    public void printDatabase() {

        Cursor cursor = cdb.getAllRows();
        final String[] fromFieldNames = new String[]{CategoryDBAdapter.COLUMN_IMG,CategoryDBAdapter.COLUMN_CategoryNAME};
        int[] toViewIDs = new int[]{R.id.waiterSelectedImage,R.id.waiterSelectedTextViewProducts};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(WaiterCategoryMenu.this, R.layout.waiter_category_item_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.waiterCategoryListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                //    ToDeleteString(SelectedItemID);
                DatabaseName name=new DatabaseName(SelectedItem);
                csdb = new CategorySelectedDBAdapter(WaiterCategoryMenu.this);
                csdb.setName(name);
               Intent m= new Intent(WaiterCategoryMenu.this,WaiterFoodMenu.class);

                m.putExtra("databaseName",SelectedItem);
                finish();
                startActivity(m);




                //make a Toast
                Toast.makeText(WaiterCategoryMenu.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_waiter_category, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WaiterCategoryMenu.this,Tables.class);
        finish();
        startActivity(i);
    }
}
