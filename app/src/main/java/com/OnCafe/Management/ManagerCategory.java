package com.OnCafe.Management;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.OnCafe.R;

/**
 * Created by Bishal on 8/11/2015.
 */
public class ManagerCategory extends AppCompatActivity {

    CategorySelectedDBAdapter csdb;
    EditText addCategory;
    CategoryDBAdapter cdb;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView browse;
    String picturePath;
    public ManagerCategory() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);
        browse=(ImageView)findViewById(R.id.browse);
        addCategory= (EditText)findViewById(R.id.addCategory);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory.setText("");
            }
        });
        Button btnAddCategory= (Button)findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String nCategory= addCategory.getText().toString();
                cdb= new CategoryDBAdapter(ManagerCategory.this);
                if (!nCategory.equals("") && !nCategory.equals("Enter New category"))
                //cdb= new CategoryDBAdapter(ManagerCategory.this);
                cdb.addCategoryAndImage(nCategory,picturePath);
                printDatabase();

            }
        });
        getSupportActionBar().setTitle("Admin/Category");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Color.parseColor("#3F51B5"))));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cdb=  new CategoryDBAdapter(ManagerCategory.this);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


if(!cdb.TableExist()) {
    int[] ImgArr = {R.drawable.bakery,
            R.drawable.soft_drinks,R.drawable.coffee_new, R.drawable.tea_new
            ,R.drawable.veg_burger,R.drawable.pizza, R.drawable.sandwich};
  //  int[] ImgArr = {R.drawable.pizza,
    //  R.drawable.soft_drinks,R.drawable.coffee, R.drawable.tea
    //,R.drawable.veg_burger,R.drawable.pizza, R.drawable.sandwich};
    String[] categoryName = {"Bakery Items", "Soft Drinks", "Coffee", "Tea","Burgers","pizza","Sandwiches"};
    int i=0;
    int image;
    for (String category : categoryName) {
        //img = getResources().getDrawable(ImgArr[i]);
        image=(ImgArr[i]);
        i++;
        cdb.addCategory(category,image);


    }

}
        cdb=  new CategoryDBAdapter(ManagerCategory.this);
        printDatabase();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();


            browse.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


        // startActivity(new Intent(getApplicationContext(),second.class));

    }
    public void ToDeleteString(int SelectedItemID)
    {
        cdb.deleteProduct(SelectedItemID);
        printDatabase();
    }


    public void printDatabase() {

        Cursor cursor = cdb.getAllRows();
        final String[] fromFieldNames = new String[]{CategoryDBAdapter.COLUMN_IMG,CategoryDBAdapter.COLUMN_CategoryNAME,CategorySelectedDBAdapter.COLUMN_ID};
        int[] toViewIDs = new int[]{R.id.waiterSelectedImage,R.id.selectedTextViewPrice};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(ManagerCategory.this, R.layout.category_item_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.CategoryListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
                int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                //    ToDeleteString(SelectedItemID);
                DatabaseName name=new DatabaseName(SelectedItem);
                csdb = new CategorySelectedDBAdapter(ManagerCategory.this);
                csdb.setName(name);
                Intent m= new Intent(ManagerCategory.this,ManagerFoodMenu.class);
                m.putExtra("DatabaseName",SelectedItem);
                finish();
                startActivity(m);




                //make a Toast
                Toast.makeText(ManagerCategory.this,
                        SelectedItem, Toast.LENGTH_SHORT).show();
            }
        });
myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) myList.getItemAtPosition(position);
        int SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[2]));
        String SelectedItem = cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[1]));
    deleteCategoryDialog d = new deleteCategoryDialog(ManagerCategory.this,SelectedItemID,SelectedItem);
        d.show();
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                printDatabase();
            }
        });
        return true;
    }
});



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

                Intent i = new Intent(ManagerCategory.this, ManagerActivity.class);
                finish();

                startActivity(i);
                return true;




            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ManagerCategory.this,ManagerActivity.class);
        finish();
        startActivity(i);
    }
}