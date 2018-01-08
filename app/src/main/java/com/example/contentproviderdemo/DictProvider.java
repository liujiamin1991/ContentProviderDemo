package com.example.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ljm on 2017-8-23.
 */
public class DictProvider extends ContentProvider
{
    private static final String TAG = "DictProvider";

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS = 1;
    private static final int WORD = 2;
    private MyDatabaseHelper dbHelper;

    static
    {
        matcher.addURI(Words.AUTHORITY, "words", WORDS);
        matcher.addURI(Words.AUTHORITY, "word/#", WORD);
    }

    @Override
    public boolean onCreate()
    {
        Log.i(TAG, "onCreate");

        dbHelper = new MyDatabaseHelper(this.getContext(), null, Words.Word.DB_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Log.i(TAG, "query");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (matcher.match(uri))
        {
            case WORDS:
                return db.query(Words.Word.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case WORD:
                long id = ContentUris.parseId(uri);
                String wherecase = Words.Word._ID + "=" + id;
                if(selection!=null && !"".equals(selection))
                {
                    wherecase = wherecase + " and " + selection;
                }
                return db.query(Words.Word.TABLE_NAME, projection, wherecase, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("未知的Uri" + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        Log.i(TAG, "getType--->uri:" + uri);

        switch (matcher.match(uri))
        {
            case WORDS:
                return "vnd.android.cursor.dir/org.crazyit.dict";
            case WORD:
                return "vnd.android.cursor.item/org.crazyit.dict";
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        Log.i(TAG, "insert");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (matcher.match(uri))
        {
            case WORDS:
                long rowId = db.insert(Words.Word.TABLE_NAME, Words.Word._ID, values);
                if(rowId > 0)
                {
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(wordUri, null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        Log.i(TAG, "delete");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int num = 0;
        switch (matcher.match(uri))
        {
            case WORDS:
                num = db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
                break;
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                if(selection!=null && !"".equals(selection))
                {
                    whereClause = whereClause + " and " +selection;
                }
                num = db.delete(Words.Word.TABLE_NAME, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        Log.i(TAG, "update");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int num = 0;
        switch (matcher.match(uri))
        {
            case WORDS:
                num = db.update(Words.Word.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                if(selection!=null && !"".equals(selection))
                {
                    whereClause = whereClause + " and " +selection;
                }
                num = db.update(Words.Word.TABLE_NAME, values, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
}
