package com.OnCafe.Waiter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class RecordDBAdapter extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 6;
    //private static String DATABASE_NAME ="db";
    private static String DB_NAME="record.db";






  public static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    public static final String TABLE_RECORDS = "records";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";

    public static final String COLUMN_GRANDTOTAL= "grandTotal";


    //We need to pass database information along to superclass
    public RecordDBAdapter(Context context) {

        super(context,DB_PATH +DB_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_RECORDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                COLUMN_TIME + " TEXT, " +

                COLUMN_GRANDTOTAL + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS + ";");
        onCreate(db);
    }

    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_RECORDS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS + ";");
        onCreate(db);
    }




public Cursor get_total(String tableNum){

      try {
          SQLiteDatabase db;


          db = SQLiteDatabase.openDatabase(DB_PATH + "Table" + tableNum + ".db", null,
                  SQLiteDatabase.OPEN_READONLY);
          Cursor cur = db.rawQuery("SELECT SUM(" + COLUMN_GRANDTOTAL + ") FROM " + TABLE_RECORDS, null);
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
        db.execSQL("DELETE FROM " + TABLE_RECORDS + " WHERE " + COLUMN_ID + "=\"" + selectedId + "\";");
    }

    //Add a new row to the database
    public void save(String time,int total) {


        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_GRANDTOTAL, total);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_RECORDS, null, values);
        db.close();
    }
    public Cursor get_sum(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM("+ COLUMN_GRANDTOTAL + ") FROM "+ TABLE_RECORDS , null);
        return cur;

    }


    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db = getWritableDatabase();
        String[] ALL_COLUMNS = new String[]
        {
                COLUMN_ID,

                COLUMN_TIME,
                COLUMN_GRANDTOTAL
        };
        Cursor c = db.query(TABLE_RECORDS, ALL_COLUMNS, where, null, null, null, null);
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

    cur = db.rawQuery("select * from " + TABLE_RECORDS + ";", null);
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














