package jajodia.aditya.com.bunkmanager2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by kunalsingh on 14/12/16.
 */

public class NotificationReciever extends BroadcastReceiver {
    public static final String TAG="Broadcast";
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
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(context, DayInput.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 50, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentIntent(pendingIntent);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("Time for Attendance");
                builder.setContentText("click for it");


                manager.notify(50, builder.build());

            }
        }


    }

}
