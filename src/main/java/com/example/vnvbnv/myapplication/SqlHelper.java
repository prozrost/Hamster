package com.example.vnvbnv.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vnvbnv on 07.10.2015.
 */
public class SqlHelper {
    String title;
    String description;
    String image;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    private SqlHelper mDb;
    private static final String DATABASE_NAME = "DBCategory";
    private static final String DATABASE_TABLE = "categoryTable";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY," +
                    KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                    KEY_DESCRIPTION + TEXT_TYPE + COMMA_SEP + KEY_IMAGE + TEXT_TYPE +

                    " )";
    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public SqlHelper(Context c) throws SQLException {
        ourContext = c;
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SqlHelper open() throws SQLException{
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }





    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            KEY_TITLE + " TEXT NOT NULL," + KEY_DESCRIPTION + " TEXT NOT NULL," + KEY_IMAGE + " TEXT NOT NULL );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }

    public long createEntry(String title, String description,String image) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_DESCRIPTION,description);
        cv.put(KEY_IMAGE, image);

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public ArrayList<HashMap<String, String>> getAllData()
    {
        ArrayList<HashMap<String, String>> array_list = new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.ourHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from categoryTable", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            HashMap<String,String>  hashmap = new HashMap<String, String>();
            hashmap.put("title", res.getString(res.getColumnIndex(KEY_TITLE)));
            hashmap.put("description", res.getString(res.getColumnIndex(KEY_IMAGE)));
            hashmap.put("image", res.getString(res.getColumnIndex(KEY_DESCRIPTION)));




            array_list.add(hashmap);
            res.moveToNext();
        }
        return array_list;
    }
    public void deleteallrecords(){

       SQLiteDatabase db = this.ourHelper.getWritableDatabase();
            db.delete("categoryTable",null,null);
db.close();
    }
}
