package com.soumyadeeppradhan.basicbankingapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {
    private String BANKHOLDERS = "HOLDERS";
    private String HISTORY = "PASSBOOK";

    public DataBaseHandler(@Nullable Context context) {
        super(context, "User.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BANKHOLDERS +
                " (PHONENUMBER INTEGER PRIMARY KEY ," +
                "HOLDER_NAME TEXT," +
                "HOLDER_EMAIL VARCHAR," +
                "ACCOUNT_NO VARCHAR," +
                "BANK_NAME VARCHAR," +
                "IFSC_CODE VARCHAR," +
                "ACCOUNT_BALANCE DECIMAL)");

        db.execSQL("CREATE TABLE " + HISTORY +" (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE_OF_TRANSACTION TEXT," +
                "FROM_USER TEXT," +
                "TO_USER TEXT," +
                "AMOUNT DECIMAL," +
                "TRANSACTION_STATUS TEXT," +
                "TRANSACTION_ID TEXT)");

        String Soumyadeep = "INSERT INTO HOLDERS VALUES(" +
                                "91002165154," +
                                "'Soumyadeep'," +
                                "'soumyadeep@pradhan.com'," +
                                "'22XXXXXX2341'," +
                                "'United Bank of World'," +
                                "'SOUM0123456'," +
                                "100.81)",

                Elon = "INSERT INTO HOLDERS VALUES(" +
                            "91565486546," +
                            "'Elon'," +
                            "'elonmusk@tesla.com'," +
                            "'90XXXXXX01441'," +
                            "'Tesla Bank'," +
                            "'TESLA123456'," +
                            "331.31)",

                Mark = "INSERT INTO HOLDERS VALUES(" +
                            "91231546545," +
                            "'Mark'," +
                            "'markzuckerberg@facebook.com'," +
                            "'21XXXXXX9978'," +
                            "'Facebook Payments Bank'," +
                            "'FACE0123456'," +
                            "406.46)",

                Ambani = "INSERT INTO HOLDERS VALUES(" +
                            "91986544665," +
                            "'Ambani'," +
                            "'mukeshambani@jio.com'," +
                            "'33XXXXXX3021'," +
                            "'Jio Payments Bank'," +
                            "'JIOR0123456'," +
                            "221.21)",

                Sundar = "INSERT INTO HOLDERS VALUES(" +
                            "91989944554," +
                            "'Sundar'," +
                            "'sundarpichai@google.com'," +
                            "'23XXXXXX1178'," +
                            "'Google Bank of India'," +
                            "'GGLE0123456'," +
                            "900.99)",

                Bernard = "INSERT INTO HOLDERS VALUES(" +
                            "91984546541," +
                            "'Bernard'," +
                            "'bernardarnault@vuitton.com'," +
                            "'09XXXXXX4570'," +
                            "'Louis Payments Bank'," +
                            "'RICH0123456'," +
                            "301.12)",

                Satya = "INSERT INTO HOLDERS VALUES(" +
                            "91854564464," +
                            "'Satya'," +
                            "'satyanadella@microsoft.com'," +
                            "'66XXXXXX3210'," +
                            "'Microsoft Bank of India'," +
                            "'MSFT0123456'," +
                            "31.13)",

                Dara = "INSERT INTO HOLDERS VALUES(" +
                        "91984654654," +
                        "'Dara'," +
                        "'darakhosrow@uber.com'," +
                        "'10XXXXXX1231'," +
                        "'Reserve Bank of Uber'," +
                        "'UBER0123456'," +
                        "9994.94)",

                Rahul = "INSERT INTO HOLDERS VALUES(" +
                        "91324654865," +
                        "'Rahul'," +
                        "'rahulceo@ofmyworld.com'," +
                        "'00XXXXXX7777'," +
                        "'Rahul Payments Bank'," +
                        "'MWLD0123456'," +
                        "787.56)",

                Kalyan = "INSERT INTO HOLDERS VALUES(" +
                        "91055465465," +
                        "'Steve'," +
                        "'steve@iphone.com'," +
                        "'23XXXXXX6666'," +
                        "'IOS Payment Banks'," +
                        "'IOSP0123456'," +
                        "45.45)";

        db.execSQL(Soumyadeep);
        db.execSQL(Elon);
        db.execSQL(Mark);
        db.execSQL(Ambani);
        db.execSQL(Sundar);
        db.execSQL(Bernard);
        db.execSQL(Satya);
        db.execSQL(Dara);
        db.execSQL(Rahul);
        db.execSQL(Kalyan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ BANKHOLDERS);
        db.execSQL("DROP TABLE IF EXISTS "+ HISTORY);
        onCreate(db);
    }

    public Cursor showAllHolders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOLDERS", null);
        return cursor;
    }

    public Cursor holderdata(String holderPhoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOLDERS where PHONENUMBER = " + holderPhoneNumber, null);
        return cursor;
    }

    public Cursor removeHolder(String holderPhoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOLDERS " +
                                        "EXCEPT SELECT * FROM HOLDERS " +
                                        "WHERE PHONENUMBER = " + holderPhoneNumber, null);
        return cursor;
    }

    public void updateBalance(String holderPhoneNumber, String updatedBalance){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE HOLDERS " +
                "SET ACCOUNT_BALANCE = " + updatedBalance +
                " WHERE PHONENUMBER = " + holderPhoneNumber);
    }

    public Cursor getPassbook(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PASSBOOK", null);
        return cursor;
    }

    public boolean transactionStatement(String date, String fromUser, String toUser, String amount, String transactionStatus, long id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("DATE_OF_TRANSACTION", date);
        values.put("FROM_USER", fromUser);
        values.put("TO_USER", toUser);
        values.put("AMOUNT", amount);
        values.put("TRANSACTION_STATUS", transactionStatus);
        values.put("TRANSACTION_ID", Long.toString(id));

        Long result = db.insert(HISTORY, null, values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
