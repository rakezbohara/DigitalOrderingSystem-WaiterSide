package com.OnCafe.Management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Environment;


public class CategoryDBAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
     static String  DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/databases/";
    private static final String DATABASE_NAME ="category.db";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_CategoryNAME = "categoryName";

    //We need to pass database information along to superclass
    public CategoryDBAdapter(Context context) {
        super(context,DB_PATH + DATABASE_NAME, null, DATABASE_VERSION);
    }
    //add imagepath to the database and the category name
    public void addCategoryAndImage(String category,String imagePath){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CategoryNAME, category);
        values.put(COLUMN_IMG, imagePath);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public  boolean CheckIsDataAlreadyInDBorNot( String fieldValue) {
        SQLiteDatabase db = getWritableDatabase();
        String Query = "Select * from " + TABLE_CATEGORIES + " where " + COLUMN_CategoryNAME + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMG + " INTEGER, " + COLUMN_CategoryNAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    //Add a new row to the database
    public void addCategory(String category,int image){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CategoryNAME, category);
        values.put(COLUMN_IMG, image);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }


    public Cursor getAllRows(){
        String where =null;
        SQLiteDatabase db = getWritableDatabase();
        String[] ALL_COLUMNS= new String[] {COLUMN_ID,COLUMN_IMG,COLUMN_CategoryNAME};
        Cursor c =db.query(TABLE_CATEGORIES,ALL_COLUMNS,where,null,null,null,null);
        if(c!=null){
            c.moveToFirst();

        }

        return c;
    }
    public void deleteProduct( int selectedItem) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_ID + "=\"" + selectedItem + "\";");
    }
    public boolean TableExist(){

        try {
            SQLiteDatabase db;


            db = SQLiteDatabase.openDatabase(DB_PATH + "category.db", null,
                    SQLiteDatabase.OPEN_READONLY);


            Cursor cur;

            cur = db.rawQuery("select * from " + TABLE_CATEGORIES + ";", null);
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
        }

    }
}









