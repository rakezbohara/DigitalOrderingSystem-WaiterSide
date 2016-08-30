package com.OnCafe.Management;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.OnCafe.R;
import com.OnCafe.Waiter.CustomDialogClass;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Bishal on 8/12/2015.
 */
public class ManagerFoodMenu extends ActionBarActivity {
String DBaseName;
    CategorySelectedDBAdapter csdb;
    EditText addProductName;
    EditText addPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_food_menu_list);
        Bundle DBName = getIntent().getExtras();

        DBaseName = DBName.getString("DatabaseName");


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
        getSupportActionBar().setTitle(DBaseName);


        csdb = new CategorySelectedDBAdapter(ManagerFoodMenu.this);

        if(!csdb.TableExist(DBaseName))
        {
          fillDefaultMenu();
        }
        csdb = new CategorySelectedDBAdapter(ManagerFoodMenu.this);
        printDatabase();
    }


    public void printDatabase() {

        Cursor cursor = csdb.getAllRows();
        final String[] fromFieldNames = new String[]{CategorySelectedDBAdapter.COLUMN_ID,
                CategorySelectedDBAdapter.COLUMN_PRODUCTNAME, CategorySelectedDBAdapter.COLUMN_PRICE};
        int[] toViewIDs = new int[]{R.id.selectedID, R.id.selectedProductList, R.id.selectedPrice};
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(ManagerFoodMenu.this, R.layout.food_menu_list_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.productList);
        myList.setAdapter(myCursorAdapter);


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                // int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                int selectedPrice = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[2]));
                int selectedID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                //    ToDeleteString(SelectedItemID);
                String sID = String.valueOf(selectedID);
                ProductClickedDialog pcd = new ProductClickedDialog(ManagerFoodMenu.this, SelectedItem, selectedPrice, sID);
                pcd.show();



                pcd.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        printDatabase();
                       // myCursorAdapter.notifyDataSetChanged();
               /*    Intent i = new Intent(getIntent());
                        finish();
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(i, 0);
                        overridePendingTransition(0,0);*/
                    }
                });

                //make a Toast
                Toast.makeText(ManagerFoodMenu.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }




public void addButtonClicked(View view){
    addProductName=(EditText)findViewById(R.id.addProductName);
    addPrice=(EditText)findViewById(R.id.addPrice);

    String newProductName=addProductName.getText().toString() ;
    String newPrice=addPrice.getText().toString() ;
if (!newProductName.equals("") && !newPrice.equals("")){
    csdb = new CategorySelectedDBAdapter(ManagerFoodMenu.this);
    csdb.addProduct(newProductName, Integer.parseInt(newPrice));
    printDatabase();

    addProductName.setText("");
    addPrice.setText("");
    Toast.makeText(ManagerFoodMenu.this,newProductName+" successfully added.",Toast.LENGTH_LONG).show();


}
    else{
    Toast.makeText(ManagerFoodMenu.this,"Field is Empty",Toast.LENGTH_LONG).show();

}
}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.back:

                Intent i = new Intent(ManagerFoodMenu.this, ManagerCategory.class);
                finish();
                startActivity(i);
                return true;




            default:
                return super.onOptionsItemSelected(item);

        }


    }



    private void fillDefaultMenu() {
        if (DBaseName.equals("Bakery Items"))
        {
            String[] productName = {"Chocolate cake","Cream Doughnuts"};
            int[] price={200,35,12};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;



            }
        }
        if (DBaseName.equals("Soft Drinks"))
        {
            String[] productName = {"Coca-Cola", "Fanta", "Dew", "Pepsi"};
            int[] price={35,35,35,35};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;

            }
        }
        if (DBaseName.equals("Coffee"))
        {
            String[] productName = {"Black Coffee", "Latte","Espresso","Milk Coffee"};
            int[] price={20,35};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;

                csdb = new CategorySelectedDBAdapter(ManagerFoodMenu.this);
                //printDatabase();
            }
        }
        if (DBaseName.equals("Tea"))
        {
            String[] productName = {"Black Tea", "Lemon Tea","Milk Tea"};
            int[] price={12,10,15};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;

            }
        }
        if (DBaseName.equals("Burgers"))
        {
            String[] productName = {"Cheese Burgers","Veg Burgers"};
            int[] price={20,35};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;



            }
        }
        if (DBaseName.equals("Sandwiches"))
        {
            String[] productName = {" Cheese Sandwiches"};
            int[] price={50};
            int  i=0;

            for (String product : productName) {


                csdb.addProduct(product,price[i]);
                i++;


            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ManagerFoodMenu.this,ManagerCategory.class);
        finish();
        startActivity(i);
    }
}
