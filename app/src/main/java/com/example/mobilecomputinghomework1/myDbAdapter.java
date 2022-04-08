package com.example.mobilecomputinghomework1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter  {

    myDbHelper helper;
    private String TABLE_NAME = "myTable";

    public myDbAdapter(Context context)
    {
        helper = new myDbHelper(context);
    }

    public long insertData(String spinner_value, String rating_value)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.SPINNER_VALUE, spinner_value);
        contentValues.put(myDbHelper.RATING_VALUE, rating_value);
        long id = dbb.insert(TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.SPINNER_VALUE,myDbHelper.RATING_VALUE};
        Cursor cursor =db.query(TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String spinner_value =cursor.getString(cursor.getColumnIndex(myDbHelper.SPINNER_VALUE));
            String  rating_value =cursor.getString(cursor.getColumnIndex(myDbHelper.RATING_VALUE));
            buffer.append(cid+ "   " + spinner_value + "   " + rating_value +" \n");
        }
        return buffer.toString();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

   static class myDbHelper extends SQLiteOpenHelper
   {
       private static final String DATABASE_NAME = "myDatabase";    // Database Name
       private static final String TABLE_NAME = "myTable";   // Table Name
       private static final int DATABASE_Version = 1;    // Database Version
       private static final String UID="_id";     // Column I (Primary Key)
       private static final String SPINNER_VALUE = "Spinner";    //Column II
       private static final String RATING_VALUE= "Rating";    // Column III
       private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
               " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SPINNER_VALUE+" VARCHAR(255) ,"+ RATING_VALUE+" VARCHAR(225));";
       private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
       private Context context;

       public myDbHelper(Context context) {
           super(context, DATABASE_NAME, null, DATABASE_Version);
           this.context=context;
           Message.message(context,"Started...");
       }

       @Override
       public void onCreate(SQLiteDatabase db) {
           try {
               db.execSQL(CREATE_TABLE);
               Message.message(context,"TABLE CREATED");
           } catch (Exception e) {
              Message.message(context,""+e);
           }
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          /* try {
               Message.message(context,"OnUpgrade");
               db.execSQL(DROP_TABLE);
               onCreate(db);
           }catch (Exception e) {
               Message.message(context,""+e);
                          }*/
       }
   }
}
