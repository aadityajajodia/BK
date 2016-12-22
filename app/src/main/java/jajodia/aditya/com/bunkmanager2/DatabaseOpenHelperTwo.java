package jajodia.aditya.com.bunkmanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kunalsingh on 14/12/16.
 */

public class DatabaseOpenHelperTwo extends SQLiteOpenHelper {

    public static final String DB_TWO_NAME = "TimeTable";

    public static final int DB_VER = 1;



    static DatabaseOpenHelperTwo databaseOpenHelperTwo;
    public DatabaseOpenHelperTwo(Context context) {
        super(context,DB_TWO_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TimeTable.CREATE_TABLE_CMD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static long insertData(Context c , int day , String periodOne,String periodTwo,String periodThree,String periodFour,String periodFive,String periodSix,String periodSeven,String periodEight){

        databaseOpenHelperTwo = new DatabaseOpenHelperTwo(c);
        SQLiteDatabase database = databaseOpenHelperTwo.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TimeTable.Columns.DAY,day);
        values.put(TimeTable.Columns.PERIOD_ONE,periodOne);
        values.put(TimeTable.Columns.PERIOD_TWO,periodTwo);
        values.put(TimeTable.Columns.PERIOD_THREE,periodThree);
        values.put(TimeTable.Columns.PERIOD_FOUR,periodFour);
        values.put(TimeTable.Columns.PERIOD_FIVE,periodFive);
        values.put(TimeTable.Columns.PERIOD_SIX,periodSix);
        values.put(TimeTable.Columns.PERIOD_SEVEN,periodSeven);
        values.put(TimeTable.Columns.PERIOD_EIGHT,periodEight);
        long t = database.insert(TimeTable.TABLE_NAME,null,values);
        return t;

    }

    public static Cursor readData(Context c , int day){

        databaseOpenHelperTwo = new DatabaseOpenHelperTwo(c);

        SQLiteDatabase database = databaseOpenHelperTwo.getReadableDatabase();

        String projection[]={
                TimeTable.Columns.DAY,
                TimeTable.Columns.PERIOD_ONE,
                TimeTable.Columns.PERIOD_TWO,
                TimeTable.Columns.PERIOD_THREE,
                TimeTable.Columns.PERIOD_FOUR,
                TimeTable.Columns.PERIOD_FIVE,
                TimeTable.Columns.PERIOD_SIX,
                TimeTable.Columns.PERIOD_SEVEN,
                TimeTable.Columns.PERIOD_EIGHT
        };

        String selection = TimeTable.Columns.DAY +" = ?";
        String selectionArgs[] = {String.valueOf(day)};

        Cursor cursor = database.query(TimeTable.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

        return  cursor;
    }

    public static Cursor readAllRows(Context c){


        databaseOpenHelperTwo = new DatabaseOpenHelperTwo(c);

        SQLiteDatabase database = databaseOpenHelperTwo.getReadableDatabase();

        String projection[]={
          TimeTable.Columns.DAY,
                TimeTable.Columns.PERIOD_ONE,
                TimeTable.Columns.PERIOD_TWO,
                TimeTable.Columns.PERIOD_THREE,
                TimeTable.Columns.PERIOD_FOUR,
                TimeTable.Columns.PERIOD_FIVE,
                TimeTable.Columns.PERIOD_SIX,
                TimeTable.Columns.PERIOD_SEVEN,
                TimeTable.Columns.PERIOD_EIGHT
        };

        Cursor cursor = database.query(TimeTable.TABLE_NAME,projection,null,null,null,null,null);

        return cursor;

    }

    public static long updateData(Context c , int day,String periodOne,String periodTwo,String periodThree,String periodFour,String periodFive,String periodSix,String periodSeven,String periodEight){

        databaseOpenHelperTwo = new DatabaseOpenHelperTwo(c);

        SQLiteDatabase database = databaseOpenHelperTwo.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TimeTable.Columns.PERIOD_ONE,periodOne);
        values.put(TimeTable.Columns.PERIOD_TWO,periodTwo);
        values.put(TimeTable.Columns.PERIOD_THREE,periodThree);
        values.put(TimeTable.Columns.PERIOD_FOUR,periodFour);
        values.put(TimeTable.Columns.PERIOD_FIVE,periodFive);
        values.put(TimeTable.Columns.PERIOD_SIX,periodSix);

        String whereClause =  TimeTable.Columns.DAY+" =?";

        String whereArgs[] = {String.valueOf(day)};

        long t  = database.update(TimeTable.TABLE_NAME,values,whereClause,whereArgs);

        return t;
    }

}
