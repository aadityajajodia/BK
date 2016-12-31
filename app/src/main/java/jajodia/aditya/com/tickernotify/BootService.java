package jajodia.aditya.com.tickernotify;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by kunalsingh on 31/12/16.
 */

public class BootService extends IntentService {

    public static final String TAG="BootService";


    public BootService() {
        super("BootService");
    }

    public void setAlarm(int hr,int min,int sec){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,sec);

        Log.d(TAG,"came in alarm 2");
        Intent intent = new Intent(getApplicationContext(),NotificationReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),50,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"came in onHandle Intent");
        setAlarm(21,0,0);
        Intent service = new Intent(this,BootService.class);
        stopService(service);

    }
}
