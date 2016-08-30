package com.OnCafe.Management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class CategorySelectedDBAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //private static String DATABASE_NAME ="db";
    private static String DB_NAME;

    public void setName(DatabaseName name) {
        DB_NAME = name.get_name()+".db";

    }




  public static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    public static final String TABLE_PRODUCTS = "products";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_PRICE = "price";

    //We need to pass database information along to superclass
    public CategorySelectedDBAdapter(Context context) {

        super(context,DB_PATH +DB_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT, " + COLUMN_PRICE + " INTEGER " +
                ");";
        db.execSQL(query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS + ";");
        onCreate(db);
    }

    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS + ";");
        onCreate(db);
    }


    //Delete a product from the database
    public void deleteProduct(int selectedId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + selectedId + "\";");
    }

    //Add a new row to the database
    public void addProduct(String products,int price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, products);
        values.put(COLUMN_PRICE, price);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();

    }

public void updateRecord(String newProductName,int newPrice,int id){
    SQLiteDatabase db= getWritableDatabase();
    ContentValues cv= new ContentValues();
    cv.put(COLUMN_PRODUCTNAME,newProductName);
    cv.put(COLUMN_PRICE,newPrice);
    db.update(TABLE_PRODUCTS,cv, "_id = " + id , null);


}
    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] ALL_COLUMNS = new String[]{COLUMN_ID, COLUMN_PRODUCTNAME,COLUMN_PRICE};
        Cursor c = db.query(TABLE_PRODUCTS, ALL_COLUMNS, where, null, null, null, null);
        if (c != null) {
            c.moveToFirst();

        }

        return c;
    }



    public boolean TableExist(String DBaseName){

try {
    SQLiteDatabase db;


    db = SQLiteDatabase.openDatabase(DB_PATH + DBaseName+".db", null,
            SQLiteDatabase.OPEN_READONLY);


    Cursor cur;

    cur = db.rawQuery("select * from " + TABLE_PRODUCTS + ";", null);
    if (cur != null) {
        if (cur.getCount() > 0) {
            cur.close();
            return true;
        }
        cur.close();
    }
    return false;

} catch(SQLiteException e){

            return false;
        }//return cur != null;

    }


}














