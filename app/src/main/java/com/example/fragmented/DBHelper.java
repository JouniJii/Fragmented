package com.example.fragmented;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "omatietokanta.db";
    private static final String TABLE_NAME = "oma_table";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "NAME TEXT,"
            + "DATE TEXT)";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "DATE";
    private static final String GET_ALL_DATA_QUERY = "SELECT * FROM " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //Log.i(TAG, "DBHelper: constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        //Log.i(TAG, "onCreate: create table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //Log.i(TAG, "onUpgrade: ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteTable () {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
    }

    public void addData (String nimi, String pvm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, nimi);
        contentValues.put(COL_3, pvm);
        db.insert(TABLE_NAME, null, contentValues);
        //Log.i(TAG, "addData: lisätty dataa: " + nimi + " " + pvm);

    }

    public ArrayList<playerObj> getAllData() {
        ArrayList<playerObj> po_list = new ArrayList<playerObj>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_DATA_QUERY, null);

        while (cursor.moveToNext()) {

            playerObj po = new playerObj(cursor.getString(1), cursor.getString(2));
            po_list.add(po);
        }

        if (cursor != null) {
            cursor.close();
        }
        //Log.i(TAG, "getAllData: array size: " + po_list.size());
        return po_list;
    }
}