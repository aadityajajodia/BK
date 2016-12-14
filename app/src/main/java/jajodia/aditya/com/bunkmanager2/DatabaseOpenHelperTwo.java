package jajodia.aditya.com.bunkmanager2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kunalsingh on 14/12/16.
 */

public class DatabaseOpenHelperTwo extends SQLiteOpenHelper {

    public static final String DB_TWO_NAME = "TimeTable";

    public static final int DB_VER = 1;



    DatabaseOpenHelperTwo databaseOpenHelperTwo;
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

    public long insertData(Context c , int day , String period[]){

        databaseOpenHelperTwo = new DatabaseOpenHelperTwo(c);
        SQLiteDatabase database = databaseOpenHelperTwo.getWritableDatabase();

        return 0;
    }
}
