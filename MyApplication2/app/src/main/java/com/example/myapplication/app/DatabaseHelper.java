package com.example.myapplication.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Christopher on 6/17/2014.
 */
public final class DatabaseHelper extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ContactDB";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_PH_NO = "PhoneNumber";
    private static final String KEY_EM_AD = "EmailAddress";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_EM_AD + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void addContact (String n, String p, String e)
    {
        //get the db
        SQLiteDatabase db = getWritableDatabase();
        //create the values to write to the db
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, n);
        values.put(KEY_PH_NO, p);
        values.put(KEY_EM_AD, e);
        //write the values to the db
        db.insert(TABLE_CONTACTS, null, values);
        //close the db
        db.close();
    }
    public void removeAllContacts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
    }
    public Intent getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Intent i = new Intent();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_EM_AD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))), cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_PH_NO)), cursor.getString(cursor.getColumnIndex(KEY_EM_AD)));
        // return contact
        i.putExtra("Contact", contact);
        return i;
    }

    public ArrayList<String> getAllContacts() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        ArrayList<String> itemList = new ArrayList<String>();
        while(cursor.moveToNext()) {
            Contact contact = new Contact(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))), cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_PH_NO)), cursor.getString(cursor.getColumnIndex(KEY_EM_AD)));
            // Adding contact to list
            itemList.add(contact.toString());
            Log.v("Name", contact.GetName());
        }
        return itemList;
    }
    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.GetName());
        values.put(KEY_PH_NO, contact.GetPhoneNumber());
        values.put(KEY_EM_AD, contact.GetEmailAddress());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.GetId()) });
    }
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.GetId()) });
        db.close();
    }
}