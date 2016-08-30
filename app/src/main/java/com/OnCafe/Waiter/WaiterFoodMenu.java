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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.Management.CategorySelectedDBAdapter;
import com.OnCafe.R;

import org.w3c.dom.Text;

/**
 * Created by Bishal on 8/13/2015.
 */
public class WaiterFoodMenu extends AppCompatActivity {
    SharedPreferences sTableNo;
    String tableNo;
    String databaseName;
    OrderedDBAdapter odbAdapter;
    int amount;
    int totalPrice;
   EditText amt;
    String SelectedItem;
    int selectedPrice;

    CategorySelectedDBAdapter csdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_food_menu);
        Bundle DBName = getIntent().getExtras();
        databaseName=DBName.getString("databaseName");
        sTableNo= getSharedPreferences("Table Number", 0);
        tableNo= sTableNo.getString("hello", "admin");




        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#72231F")));
        getSupportActionBar().setTitle("Table " + tableNo + "   " + databaseName);



        csdb = new CategorySelectedDBAdapter(WaiterFoodMenu.this);
       printDatabase();

        FloatingActionButton home=(FloatingActionButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(WaiterFoodMenu.this, new_trial.class));

            }
        });
    }

    public void printDatabase() {

        Cursor cursor = csdb.getAllRows();
        final String[] fromFieldNames = new String[]{
                CategorySelectedDBAdapter.COLUMN_PRODUCTNAME, CategorySelectedDBAdapter.COLUMN_PRICE};
        int[] toViewIDs = new int[]{R.id.waiterSelectedName,R.id.waiterSelectedPrice};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(WaiterFoodMenu.this, R.layout.waiter_food_menu_list_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.waiterFoodMenuListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
               // int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[2]));
                selectedPrice=cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                //    ToDeleteString(SelectedItemID);
                CustomDialogClass cdd=new CustomDialogClass(WaiterFoodMenu.this,SelectedItem,selectedPrice);
                cdd.show();
/*
Button btnok=(Button)findViewById(R.id.btnok);
                amt= (EditText) findViewById(R.id.amt);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       amount= Integer.parseInt(amt.getText().toString());

                        totalPrice = amount * selectedPrice;
                        odbAdapter= new OrderedDBAdapter(WaiterFoodMenu.this);
                        odbAdapter.addProduct(SelectedItem,totalPrice);

                    }
                });*/
//show per price and total price




               // OrderedProducts oProduct= new OrderedProducts(SelectedItem);



                //make a Toast
                Toast.makeText(WaiterFoodMenu.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });



    }

    /*
    public void printDatabase() {

        Cursor cursor = csdb.getAllRows();
        final String[] fromFieldNames = new String[]{CategorySelectedDBAdapter.COLUMN_ID,
                CategorySelectedDBAdapter.COLUMN_PRODUCTNAME};
        int[] toViewIDs = new int[]{R.id.waiterSelectedID, R.id.waiterSelectedProductList};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(WaiterFoodMenu.this, R.layout.waiter_food_menu_list_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.waiterFoodMenuListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                //    ToDeleteString(SelectedItemID);
                OrderTable name=new OrderTable(tableNo);
                odbAdapter = new OrderedDBAdapter(WaiterFoodMenu.this);
                odbAdapter.setName(name);
                odbAdapter=  new OrderedDBAdapter(WaiterFoodMenu.this);
                //odbAdapter=  new OrderedDBAdapter(view.getContext(),null,null,1);
                OrderedProducts oproduct= new OrderedProducts(SelectedItem);
                odbAdapter.addProduct(oproduct);



                //make a Toast
                Toast.makeText(WaiterFoodMenu.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });



    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_waiter_food_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.nextPage:
                Intent intent= new Intent(WaiterFoodMenu.this, orderTabs.class);
                finish();
                startActivity(intent);

                return true;



            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WaiterFoodMenu.this,WaiterCategoryMenu.class);
        finish();
        startActivity(i);
    }
}