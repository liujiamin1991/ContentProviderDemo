package com.example.contentproviderdemo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ljm on 2017-8-23.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper
{
    public static final String TAG = "MyDatabaseHelper";

    final String CREATE_TABLE_SQL = "create table dict(_id integer primary " + "key autoincrement , word text , detail text)";

    public MyDatabaseHelper(Context context,  SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, Words.Word.DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createSql = "create table " + Words.Word.TABLE_NAME + "(" + Words.Word._ID
                + " integer primary key autoincrement , " + Words.Word.WORD + " text , "
                + Words.Word.DETAIL + " text)";

        Log.i(TAG, "createSql:" + createSql);

        try
        {
            db.execSQL(createSql);
        }catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String dropTableSql = "drop table if exist " + Words.Word.TABLE_NAME;
        try
        {
            db.execSQL(dropTableSql);
        }catch (SQLException ex)
        {
            ex.printStackTrace();
            return;
        }

        onCreate(db);
    }
}
