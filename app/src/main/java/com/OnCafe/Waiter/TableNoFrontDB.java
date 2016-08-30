package com.OnCafe.Waiter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class TableNoFrontDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    //private static String DATABASE_NAME ="db";
    private static String DB_NAME="frontTablesWaiterHome";




  public static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    public static final String TABLE_PRODUCTS = "products";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TABLENUM = "tableNum";



    //We need to pass database information along to superclass
    public TableNoFrontDB(Context context) {

        super(context,DB_PATH + DB_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TABLENUM + " TEXT " +
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
    public void deleteProduct(String selectedName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_TABLENUM + "=\"" + selectedName + "\";");
    }

    //Add a new row to the database
    public void addProduct(String tableNum) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLENUM, tableNum);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    public  boolean CheckIsDataAlreadyInDBorNot( String fieldValue) {
        SQLiteDatabase db = getWritableDatabase();
        String Query = "Select * from " + TABLE_PRODUCTS + " where " + COLUMN_TABLENUM + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] ALL_COLUMNS = new String[]{COLUMN_ID, COLUMN_TABLENUM};
        Cursor c = db.query(TABLE_PRODUCTS, ALL_COLUMNS, where, null, null, null, null);
        if (c != null) {
            c.moveToFirst();

        }

        return c;
    }


    public boolean TableExist(String name){

try {
    SQLiteDatabase db;


    db = SQLiteDatabase.openDatabase(DB_PATH + "Table" + name +  ".db", null,
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














