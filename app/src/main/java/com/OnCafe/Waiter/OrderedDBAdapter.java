package com.OnCafe.Waiter;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class OrderedDBAdapter extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 6;
    //private static String DATABASE_NAME ="db";
    private static String DB_NAME;

    public void setName(OrderTable tableNo) {
        DB_NAME = "Table" + tableNo.get_tableNo() + ".db";

    }
    public static final String COLUMN_SERVED="serve";



  public static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    public static final String TABLE_PRODUCTS = "products";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";

    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_PERPRICE = "perPrice";


    public static final String COLUMN_AMOUNT="amount";
    public static final String COLUMN_STATUS="status";

    //We need to pass database information along to superclass
    public OrderedDBAdapter(Context context) {

        super(context,DB_PATH +DB_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_PRODUCTNAME + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_SERVED + " TEXT, " +
                COLUMN_PERPRICE + " INTEGER, " +
                COLUMN_COST + " INTEGER " +
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
   public void updateServeStatus(String id){
       SQLiteDatabase db= getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put(COLUMN_SERVED, "Served");

       db.update(TABLE_PRODUCTS,values, "_id = " + id , null);
   }
    public void updateRecord(int id,int amount,int perPrice){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values = new ContentValues();
        int cost=amount*perPrice;


        values.put(COLUMN_COST, cost);
        values.put(COLUMN_AMOUNT, amount);

        values.put(COLUMN_PERPRICE,perPrice);
        db.update(TABLE_PRODUCTS,values, "_id = " + id , null);


    }

    public void updateRecordToPreventDuplication(String name,int newAmount,int perPrice){
        SQLiteDatabase db= getWritableDatabase();

        int newCost=newAmount*perPrice;


        String rawQuery ="UPDATE products SET amount = amount + 50 WHERE productname ="+name;
        // " update " + TABLE_PRODUCTS + " set amount = amount + " + newAmount + " where "+ COLUMN_PRODUCTNAME + " = " + name;
        db.rawQuery(rawQuery, null);






    }
public Cursor get_total(String tableNum){

      try {
          SQLiteDatabase db;


          db = SQLiteDatabase.openDatabase(DB_PATH + "Table" + tableNum + ".db", null,
                  SQLiteDatabase.OPEN_READONLY);
          Cursor cur = db.rawQuery("SELECT SUM(" + COLUMN_COST + ") FROM " + TABLE_PRODUCTS, null);
          return cur;
      }
      catch(SQLiteException e){
          e.printStackTrace();
      }
    return null;
    }



    //Delete a product from the database
    public void deleteProduct(int selectedId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + selectedId + "\";");
    }

    //Add a new row to the database
    public void addProduct(int amount,String name,int perPrice) {
        int cost=amount*perPrice;

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, name);
        values.put(COLUMN_COST, cost);
        values.put(COLUMN_AMOUNT, amount);

        values.put(COLUMN_PERPRICE,perPrice);
        values.put(COLUMN_STATUS,"");
        values.put(COLUMN_SERVED,"");


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    public Cursor get_sum(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM("+ COLUMN_COST + ") FROM "+ TABLE_PRODUCTS , null);
        return cur;

    }


    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] ALL_COLUMNS = new String[]
        {
                COLUMN_ID,
                COLUMN_AMOUNT,
                COLUMN_STATUS,
                COLUMN_SERVED,
                COLUMN_PERPRICE,
                COLUMN_PRODUCTNAME,
                COLUMN_COST
        };
        Cursor c = db.query(TABLE_PRODUCTS, ALL_COLUMNS, where, null, null, null, null);
        if (c != null) {
            c.moveToFirst();

        }

        return c;
    }


    public boolean TableExist(OrderTable name){

try {
    SQLiteDatabase db;


    db = SQLiteDatabase.openDatabase(DB_PATH + "Table" + name.get_name()+ ".db", null,
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
    //for next front listview

    public boolean frontTableExist(String num){

        try {
            SQLiteDatabase db;


            db = SQLiteDatabase.openDatabase(DB_PATH + "Table" + num + ".db", null,
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
    public  boolean CheckIsDataAlreadyInDBorNot( String fieldValue) {
        SQLiteDatabase db = getWritableDatabase();
        String Query = "Select * from " + TABLE_PRODUCTS + " where " + COLUMN_PRODUCTNAME + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


}














