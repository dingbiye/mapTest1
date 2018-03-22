package com.sincodest.maptest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import org.litepal.LitePal;

import java.util.regex.Matcher;

public class MyContentProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final String AUTHORITY = "com.example.provider";

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
    }

    public MyContentProvider() {
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        SQLiteDatabase database = LitePal.getDatabase();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase database = LitePal.getDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                database.query("book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
