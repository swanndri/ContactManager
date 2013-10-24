package com.se206a3.Contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 
 * The database helper object.
 */
public class ContactDataBaseHelper extends SQLiteOpenHelper {

  public static final String TABLE_CONTACTS = "contacts";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_FIRSTNAME = "first_name";
  public static final String COLUMN_LASTNAME = "last_name";
  public static final String COLUMN_COMPANY = "company";
  public static final String COLUMN_IMAGE = "image_path";
  public static final String COLUMN_DATEOFBIRTH = "date_of_birth";
  public static final String COLUMN_PHONENUMBERS = "phone_numbers";
  public static final String COLUMN_EMAILS = "emails";
  public static final String COLUMN_ADDRESSES = "addesses";
  

  private static final String DATABASE_NAME = "Contacts.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_CONTACTS + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_FIRSTNAME + " TEXT, " 
      + COLUMN_LASTNAME + " TEXT, " 
      + COLUMN_COMPANY + " TEXT, "
      + COLUMN_IMAGE + " TEXT, "
      + COLUMN_DATEOFBIRTH + " TEXT,"
      + COLUMN_PHONENUMBERS + " TEXT, "
      + COLUMN_EMAILS + " TEXT, "
      + COLUMN_ADDRESSES + " TEXT "
      +");";

  public ContactDataBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(ContactDataBaseHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    onCreate(db);
  }

} 
