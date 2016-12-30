package jajodia.aditya.com.tickernotify;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class ContactUsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        android.app.ActionBar actionBar = getActionBar();

        actionBar.setTitle("CONTACT US");


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onBackPressed() {
         super.onBackPressed();
        }
}
