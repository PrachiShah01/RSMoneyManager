package com.TeQPrit.money;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "contactsManager.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_RECORDS = "records";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_TOTAL2 = "total2";
    private static final String KEY_DATE = "date";
    private static final String KEY_DISCRIPTION = "discription";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_CONTACT + " TEXT,"
                + KEY_TOTAL + " TEXT," + KEY_DISCRIPTION + " TEXT" + ")";
        String CREATE_USER_TABLE= "CREATE TABLE " + TABLE_RECORDS + "( "
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_CONTACT + " TEXT,"
                + KEY_TOTAL + " TEXT," + KEY_DATE + " TEXT,"+ KEY_TOTAL2 + " TEXT," + KEY_DISCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    private static final String DATABASE_ALTER = "ALTER TABLE "
            + TABLE_RECORDS + " ADD COLUMN " + KEY_DISCRIPTION + " string;";
    private static final String DATABASE_ALTER2 = "ALTER TABLE "
            + TABLE_CONTACTS + " ADD COLUMN " + KEY_DISCRIPTION + " string;";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 4) {
            db.execSQL(DATABASE_ALTER);
            db.execSQL(DATABASE_ALTER2);
        }
    }


    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_TOTAL, contact.get_total());
        values.put(KEY_CONTACT,contact.get_contact());
        values.put(KEY_DISCRIPTION,contact.get_discription());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    void addRecord(Record record){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, record.getName()); // Contact Name
        values.put(KEY_TOTAL, record.get_total());// Contact Phone
        values.put(KEY_DATE,record.get_date());
        values.put(KEY_CONTACT,record.get_contact());
        values.put(KEY_TOTAL2,record.get_total2());
        values.put(KEY_DISCRIPTION,record.getDiscription());
        db.insert(TABLE_RECORDS, null, values);
        db.close();
    }


    public List<Record> getAllRecords() {
        List<Record> contactList = new ArrayList<Record>();

        String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Record record = new Record();
                record.setID(Integer.parseInt(cursor.getString(0)));
                record.setName(cursor.getString(1));
                record.set_total(cursor.getString(3));
                record.set_date(cursor.getString(4));
                record.set_total2(cursor.getString(5));
                record.set_contact(cursor.getString(2));

                String dummy = cursor.getString(6);

                record.setDiscription(dummy);

                contactList.add(record);
            } while (cursor.moveToNext());
        }
        return contactList;
    }


    void deletecon(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{""+i};
        db.delete(TABLE_CONTACTS,KEY_ID+"=?",args);
    }


    void deleterec(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{""+i};
        db.delete(TABLE_RECORDS,KEY_ID+"=?",args);
    }


    void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_RECORDS);
        db.close();
    }


    void updatecon(String name,String contact,String s1,String s2,String idd,String s13) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        int s3 = Integer.valueOf(getContact(Integer.valueOf(idd)).get_total()) - Integer.valueOf(s2) + Integer.valueOf(s1) ;
        String s4 = ""+s3;
        values.put(KEY_NAME, name);
        values.put(KEY_TOTAL, s4);
        values.put(KEY_CONTACT,contact);
        values.put(KEY_DISCRIPTION,s13);

        String[] args = new String[]{""+idd};
        db.update(TABLE_CONTACTS,values,KEY_ID+"=?",args);
        db.close();
    }


    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_CONTACT, KEY_TOTAL, KEY_DISCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(3),cursor.getString(2), cursor.getString(4));

        return contact;
    }


    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.set_total(cursor.getString(3));
                contact.set_contact(cursor.getString(2));

                String dummy = cursor.getString(4);

                contact.set_discription(dummy);

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

}

