package com.example.textstream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    public static final String dbname = "Signup.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbname, null, 1); // Ensure the dbname is used here
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Updated table schema without the 'username' field
        db.execSQL("CREATE TABLE allUsers (name TEXT, email TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table and recreate it if the database schema changes
        db.execSQL("DROP TABLE IF EXISTS allUsers");
        onCreate(db);
    }

    // Insert data into the database (name, email, password)
    public boolean insertData(String name, String email, String password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);        // Insert the 'name'
        contentValues.put("email", email);      // Insert the 'email'
        contentValues.put("password", password); // Insert the 'password'

        // Insert data and check for errors
        long result = mydb.insert("allUsers", null, contentValues);

        return result != -1; // Return true if insert was successful
    }

    // Check if the email already exists in the database
    public boolean checkEmail(String email) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM allUsers WHERE email=?", new String[]{email});

        return cursor.getCount() > 0; // Return true if email exists
    }

    // Check if the provided email and password match an existing record
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM allUsers WHERE email=? AND password=?", new String[]{email, password});

        return cursor.getCount() > 0; // Return true if email and password match
    }
}