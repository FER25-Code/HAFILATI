package com.map.rsd.busapplication.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.DateTimeKeyListener;

import com.android.volley.Response;

import org.json.JSONArray;

import java.sql.Blob;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import androidx.annotation.Nullable;

public class DB_sqlite extends SQLiteOpenHelper {
    public static final String DBname = "data.db";
    public static final String TAblename = "Event";
    public static final String CL1 = "Id";
    public static final String CL2 = "Decription";
    public static final String CL3 = "StartDate";
    public static final String CL4 = "EndDate";
    public static final String CL5 = "name";
    public static final int VERSION = 1;
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     */
    public DB_sqlite(@Nullable Context context) {
        super(context, DBname,null , VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Event (id INTEGER PRIMARY KEY , Decription TEXT,StartDate DATETIME,EndDate DATETIME,name TEXT )");

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public final boolean IinsertEvent(String Decription, String startdate, String EndDate, String name ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CL2, Decription);
        contentValues.put(CL3, String.valueOf(startdate));
        contentValues.put(CL4, String.valueOf(EndDate));
        contentValues.put(CL5,name);
        long result = db.insert(TAblename, null, contentValues);

        if (result == -1)
            return false;
        else {

            return true;
        }
    }
}
