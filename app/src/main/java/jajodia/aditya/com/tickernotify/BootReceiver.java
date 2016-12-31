package jajodia.aditya.com.tickernotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kunalsingh on 31/12/16.
 */

public class BootReceiver extends BroadcastReceiver {

    public static final String TAG="BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG,"came in on Receive"+" "+intent.getAction());
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")||intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")){
            Toast.makeText(context, "Came", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"came in if");
            Intent intent1 = new Intent(context,BootService.class);
            context.startService(intent1);
        }

    }
}
