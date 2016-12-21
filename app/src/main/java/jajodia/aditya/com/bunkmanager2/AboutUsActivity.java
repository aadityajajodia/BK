package jajodia.aditya.com.bunkmanager2;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        android.app.ActionBar actionBar = getActionBar();

        actionBar.setTitle("ABOUT US");
    }
}
