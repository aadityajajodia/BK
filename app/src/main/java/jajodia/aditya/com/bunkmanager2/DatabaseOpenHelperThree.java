package jajodia.aditya.com.bunkmanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kunalsingh on 21/12/16.
 */

public class DatabaseOpenHelperThree extends SQLiteOpenHelper {

    public static final String DB_NAME="DatabaseThree";

    public static final int DB_VER=1;
    private static DatabaseOpenHelperThree databaseOpenHelperThree;


    public DatabaseOpenHelperThree(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(WeekPeriodTable.CREATE_TABLE_CMD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static long insertData(Context context,int day , int periodOne,int periodTwo,int periodThree,int periodFour,int periodFive,int periodSix,int periodSeven,int periodEight){

        databaseOpenHelperThree = new DatabaseOpenHelperThree(context);

        SQLiteDatabase database = databaseOpenHelperThree.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(WeekPeriodTable.Columns.DAY,day);
        values.put(WeekPeriodTable.Columns.PERIOD_ONE,periodOne);
        values.put(WeekPeriodTable.Columns.PERIOD_TWO,periodTwo);
        values.put(WeekPeriodTable.Columns.PERIOD_THREE,periodThree);
        values.put(WeekPeriodTable.Columns.PERIOD_FOUR,periodFour);
        values.put(WeekPeriodTable.Columns.PERIOD_FIVE,periodFive);
        values.put(WeekPeriodTable.Columns.PERIOD_SIX,periodSix);
        values.put(WeekPeriodTable.Columns.PERIOD_SEVEN,periodSeven);
        values.put(WeekPeriodTable.Columns.PERIOD_EIGHT,periodEight);

    long t=database.insert(WeekPeriodTable.TABLE_NAME,null,values);

        return t;

    }

    public static Cursor readData(Context context , int day){

        databaseOpenHelperThree = new DatabaseOpenHelperThree(context);
        SQLiteDatabase database = databaseOpenHelperThree.getReadableDatabase();

        String selection = WeekPeriodTable.Columns.DAY+"  =?";
        String selectionArgs[] = {String.valueOf(day)};

        String projection[]={
                WeekPeriodTable.Columns.PERIOD_ONE,
                WeekPeriodTable.Columns.PERIOD_TWO,
                WeekPeriodTable.Columns.PERIOD_THREE,
                WeekPeriodTable.Columns.PERIOD_FOUR,
                WeekPeriodTable.Columns.PERIOD_FIVE,
                WeekPeriodTable.Columns.PERIOD_SIX,
                WeekPeriodTable.Columns.PERIOD_SEVEN,
                WeekPeriodTable.Columns.PERIOD_EIGHT

        };

        Cursor cursor = database.query(WeekPeriodTable.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        return cursor;
    }

    public static long upgradeData(Context context , int day , int periodOne,int periodTwo,int periodThree,int periodFour,int periodFive,int periodSix,int periodSeven,int periodEight){

        databaseOpenHelperThree = new DatabaseOpenHelperThree(context);

        SQLiteDatabase database = databaseOpenHelperThree.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(WeekPeriodTable.Columns.DAY,day);
        values.put(WeekPeriodTable.Columns.PERIOD_ONE,periodOne);
        values.put(WeekPeriodTable.Columns.PERIOD_TWO,periodTwo);
        values.put(WeekPeriodTable.Columns.PERIOD_THREE,periodThree);
        values.put(WeekPeriodTable.Columns.PERIOD_FOUR,periodFour);
        values.put(WeekPeriodTable.Columns.PERIOD_FIVE,periodFive);
        values.put(WeekPeriodTable.Columns.PERIOD_SIX,periodSix);
        values.put(WeekPeriodTable.Columns.PERIOD_SEVEN,periodSeven);
        values.put(WeekPeriodTable.Columns.PERIOD_EIGHT,periodEight);

        String where = WeekPeriodTable.Columns.DAY +"=?";
        String whereArgs[] = {String.valueOf(day)};

        long t   = database.update(WeekPeriodTable.TABLE_NAME,values,where,whereArgs);

        return t;
    }

    public static long deleteAllRows(Context c){

        databaseOpenHelperThree = new DatabaseOpenHelperThree(c);

        SQLiteDatabase database = databaseOpenHelperThree.getWritableDatabase();

        long t = database.delete(WeekPeriodTable.TABLE_NAME,null,null);

        return t;
    }



}
