package com.example.contentproviderdemo;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ljm on 2017-8-23.
 */
public final class Words
{
    public static final String AUTHORITY = "org.crazyit.provides.dictprovider";

    public static final class Word implements BaseColumns
    {
        public final static String DB_NAME = "provider";
        public final static String TABLE_NAME = "dict";
        public final static String _ID = "_id";
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
        public final static int DB_VERSION = 1;

        public final static Uri DICT_CONNECT_URI = Uri.parse("content://" + AUTHORITY + "/words");
        public final static Uri WORD_CONNECT_URI = Uri.parse("content://" + AUTHORITY + "/word");
    }
}
