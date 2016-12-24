package jajodia.aditya.com.bunkmanager2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by kunalsingh on 14/12/16.
 */

public class NotificationReciever extends BroadcastReceiver {
    public static final String TAG="Broadcast";
    static NotificationManager manager;


    public static final String FILE="notify";
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d(TAG, "onRecieve : " + "received");

         int day = DayInput.setDate();

        if (day != 7) {

            Cursor cursor = DatabaseOpenHelperTwo.readData(context, day);

            cursor.moveToFirst();

            boolean st = true;
            for (int i = 1; i <= 8; i++) {
                if (!cursor.getString(i).isEmpty()) {
                    st = false;
                    break;
                }
            }
            if (!st) {

                makeNotifiaction(context,day);

            }
        }else{

            for(int i=0;i<8;i++){

                Cursor cursor = DatabaseOpenHelperThree.readData(context,i+1);
                cursor.moveToFirst();
                if(cursor.getCount()<=0)
                    DatabaseOpenHelperThree.insertData(context,i+1,0,0,0,0,0,0,0,0);
                else
                    DatabaseOpenHelperThree.upgradeData(context,i+1,0,0,0,0,0,0,0,0);
            }

        }


    }

    public static int periodsDone(int day, Context context){

        Cursor cursor = DatabaseOpenHelperThree.readData(context,day);

        cursor.moveToFirst();

        int c=0;
        for(int i=0;i<8;i++){

            c = c+cursor.getInt(i);
        }
        return c;
    }

    public static void makeNotifiaction(Context context,int day) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, DayInput.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 50, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(Color.parseColor("#03a9f4"));
        builder.setOngoing(true);
        builder.setContentTitle("Mark Today's Attendance");


        Cursor cursor1 = DatabaseOpenHelperTwo.readData(context, day);
        cursor1.moveToFirst();
        int t = 0;
        for (int i = 1; i < 9; i++) {
            if (!cursor1.getString(i).isEmpty()) {
                t++;
            }
        }
        int l = NotificationReciever.periodsDone(day, context);
        Intent intent = new Intent(context, TotalInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("STATUS", true);

        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 60, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(0, "No classes Today", pendingIntent1);
        builder.addAction(0, "Mark it now", pendingIntent);
        if ((t - l) == 0)
            manager.cancelAll();
        else {
            //builder.setStyle(new NotificationCompat.BigTextStyle().bigText("You still have " + (t - l) + " left unmarked"));
            builder.setContentText("You still have " + (t - l) + " left unmarked");
            manager.notify(50, builder.build());
            if(l!=0){
                builder.mActions.clear();
                builder.addAction(0, "Mark it now", pendingIntent);
                manager.notify(50,builder.build());
            }
        }


    }
    }

