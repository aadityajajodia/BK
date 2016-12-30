package jajodia.aditya.com.tickernotify;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by kunalsingh on 22/12/16.
 */

public class SettingsActivity extends PreferenceActivity {

    private static final String TAG ="SettingActivity" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);


    }
}
