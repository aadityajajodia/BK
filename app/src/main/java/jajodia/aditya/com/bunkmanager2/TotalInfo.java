package jajodia.aditya.com.bunkmanager2;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import org.w3c.dom.Text;

public class TotalInfo extends FragmentActivity {


    private static final String TAG = "TotalInfo";
    RelativeLayout li;
    int size;
    static String subjects[];
    WindowManager windowManager ;
    public boolean exit = false;
    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        if(exit){
            finish();
        }else{

            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            },3*1000);

        }



    }

    @Override
    protected void onResume() {
      //  invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(TAG,"onCreateOptionsMenu"+" " +"came in menu");
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.share);
        ActionProvider shareActionProvider = item.getActionProvider();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch(id) {

            case R.id.item_edit : editAlertDialog();
                                    break;

            case R.id.share : Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                String shareBody = "Share context";
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Please download the app");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                                startActivity(Intent.createChooser(shareIntent,"Share Via"));
                                break;

            case R.id.item_about : Intent intent = new Intent(this,AboutUsActivity.class);
                                                  //  overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                                    startActivity(intent);
                                                    break;

            case R.id.contact_us : Intent intent1 = new Intent(this,ContactUsActivity.class);
                                    startActivity(intent1);
                                    break;
            case R.id.settings : Intent in = new Intent(this,SettingsActivity.class);
                                    //startActivity(in);
                                    break;

            default:
                Toast.makeText(this, "Not came", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //   MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_total_info);


        Intent in = getIntent();
        boolean bol = in.getBooleanExtra("STATUS",false);
        if(bol) {
            Log.d(TAG,"came to exit notification");
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(50);

        }
            //     ActionBar actionBar = getSupportActionBar();




        Cursor cursor = DatabaseOpenHelperTwo.readData(this,2);
        if(cursor.getCount()<=0){
            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }else {

            windowManager = getWindowManager();
            generateAdd();


            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_FILE, 0);
            String s = sharedPreferences.getString("Subjects", MainActivity.getSubjects());
            final String subject = s; // subjects as a String separated by $
            size = sharedPreferences.getInt("Size", MainActivity.size);
            Log.d(TAG, s + " " + "12" + " " + size);
            subjects = new String[size];
            int l = subject.length();
            int j = 0;
            int k = 0;
            // Log.d(TAG,subject);
            for (int i = 0; i < l; i++) {
                Log.d(TAG, "SEE" + " " + i);
                if (subject.charAt(i) == '$') {
                    subjects[k++] = subject.substring(j, i);
                    j = i + 1;
                    Log.d(TAG, "string came" + " " + subjects[k - 1]);

                }
            }

            li = (RelativeLayout) findViewById(R.id.activity_total_info);
            RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[size];
            final Button btn[] = new Button[size];
            for (int i = 0; i < size; i++) {
                //   Log.d(TAG,"sss"+" " +i);
                btn[i] = new Button(this);
                btn[i].setId((i + 1) * 4);
                final int w = getWidth();
                Log.d(TAG, "width : " + w);
                params[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (i % 4 == 0 && i > 0) {
                    //    Log.d(TAG,"came "+" "+i);
                    params[i].addRule(RelativeLayout.BELOW, btn[i - 4].getId());

                } else if (i > 0 && i > 3) {
                    params[i].addRule(RelativeLayout.RIGHT_OF, btn[i - 1].getId());
                    params[i].addRule(RelativeLayout.BELOW, btn[i - 4].getId());
                } else if (i > 0) {
                    params[i].addRule(RelativeLayout.RIGHT_OF, btn[i - 1].getId());
                } else {
                    params[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                if (subjects[i].length() <= 5)
                    btn[i].setText(subjects[i]);
                else {
                    btn[i].setText(subjects[i].substring(0, 3) + "..");
                }

                btn[i].setTextSize(12);
                boolean b = percentattendance(subjects[i]);
                if (b)
                    btn[i].setTextColor(Color.parseColor("#FF5722"));
                btn[i].setLayoutParams(params[i]);
                li.addView(btn[i], params[i]);
                final int finalI = i;
                btn[i].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d(TAG, "action : " + event.getAction());
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_UP:
                                Log.d(TAG, "came in case 2");
                                btn[finalI].setElevation(0);
                                btn[finalI].setWidth(w / 4);
                                if(subjects[finalI].length()<=5)
                                    btn[finalI].setText(subjects[finalI]);
                                else
                                    btn[finalI].setText(subjects[finalI].substring(0, 3) + "..");

                                return true;
                            case MotionEvent.ACTION_MOVE:
                                Log.d(TAG, "came in case 1");
                                btn[finalI].setElevation(20);
                                btn[finalI].setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
                                btn[finalI].setText(subjects[finalI]);
                                return true;
                            default:
                                btn[finalI].setPressed(false);
                                Cursor c = DatabaseOpenHelper.readData(TotalInfo.this, subjects[finalI]);

                                c.moveToFirst();

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                if (c.getCount() <= 0) {
                                    btn[finalI].setTextColor(Color.WHITE);
                                    Log.d(TAG, "came in 1");
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                    ButtonFragment fragment = new ButtonFragment();
                                    fragment.setSubject(subjects[finalI]);
                                    //   Log.d(TAG,"button called"+" "+finalI);
                                    fragmentTransaction.replace(R.id.frame_layout, fragment, null);
                                    fragmentTransaction.commit();
                                } else {
                                    boolean b = percentattendance(subjects[finalI]);
                                    if (b)
                                        btn[finalI].setTextColor(Color.parseColor("#FF5722"));
                                        else{
                                        btn[finalI].setTextColor(Color.WHITE);
                                    }
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//
                                    Log.d(TAG, "came in 2");
                                    ButtonFragmentTwo fragmentTwo = new ButtonFragmentTwo();
                                    fragmentTwo.setSubject(subjects[finalI]);
                                    fragmentTransaction.replace(R.id.frame_layout, fragmentTwo, null);
                                    fragmentTransaction.commit();
                                }
                                c.close();
                                return true;
                        }

                    }
                });
            }


            // adding fragments for each buttons
            final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, btn[size - 1].getId());
            frameLayout.setLayoutParams(layoutParams);
            if (frameLayout.getParent() != null) {
                ((ViewGroup) frameLayout.getParent()).removeView(frameLayout);
            }
            li.addView(frameLayout);

           // DialogFragment dialogFragment = DialogFragmentOne.newInstance();
            //dialogFragment.show(getSupportFragmentManager(),"check");
        }
    }

public static String[] getSubjects(){
    return subjects;
}


    public void generateAdd(){

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-4601836836916539~2310735402");

        AdView adView = (AdView)findViewById(R.id.ad_view);
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public int getWidth(){
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        return size.x;
    }

    public boolean percentattendance(String sub){

        Cursor cursor = DatabaseOpenHelper.readData(this,sub);
        cursor.moveToFirst();

        if(cursor.getCount()<=0)
            return false;
        else{

            int t = cursor.getInt(2);
            int p = cursor.getInt(3);

            float att = ((float)p/t)*100;

            if(att>=75.0)
                return false;
            else
                return true;
        }

    }


    public void editAlertDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_BACK)
                    dialog.cancel();

                return true;
            }
        });
        String [] edit = {"Edit Attendance","Edit TimeTable"};

        builder.setItems(edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    switch (which){

                        case 0 :editAttendanceDialog();
                           // Toast.makeText(TotalInfo.this, "You clicked for attendace", Toast.LENGTH_SHORT).show();
                            break;
                        case 1 :editTimeTableDialog();
                           // Toast.makeText(TotalInfo.this, "You clicked for timetable", Toast.LENGTH_SHORT).show();
                            break;
                    default:
                        Toast.makeText(TotalInfo.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                        break;
                    }

            }
        });

        builder.create();
        builder.show();

    }

    public void editAttendanceDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_BACK)
                    dialog.cancel();

                return true;
            }
        });
        builder.setTitle("Subjects");
        builder.setItems(subjects, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                        editTotalPresentDialog(which);


            }
        });

        builder.create();
        builder.show();

    }

    public void editTotalPresentDialog(final int sub){


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater inflater = this.getLayoutInflater();

        View view = inflater.inflate(R.layout.alert_edit_attendace,null);
        builder.setCancelable(false);
        builder.setTitle(subjects[sub]);

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_BACK)
                    dialog.cancel();

                return true;
            }
        });
        builder.setView(view);

        final EditText et_t = (EditText)view.findViewById(R.id.et_total_alert);
        final EditText et_p = (EditText)view.findViewById(R.id.et_present_alert);

        final Cursor c = DatabaseOpenHelper.readData(TotalInfo.this,subjects[sub]);
        c.moveToFirst();
        if(c.getCount()>0) {
            et_t.setHint(String.valueOf(c.getInt(2)));
            et_p.setHint(String.valueOf(c.getInt(3)));

           // et_t.setHintTextColor(Color.parseColor("#000080"));
            //et_p.setHintTextColor(Color.parseColor("#000080"));
        }else {
            et_t.setHint("0");
            et_p.setHint("0");
        }
            builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(et_p.getText().toString().isEmpty()||et_t.getText().toString().isEmpty())
                    Toast.makeText(TotalInfo.this, "Please enter new total and present ", Toast.LENGTH_SHORT).show();
                else {
                    int t = Integer.parseInt((et_t.getText().toString()));
                    int p = Integer.parseInt((et_p.getText().toString()));
                    if(t==0)
                        Toast.makeText(TotalInfo.this, "Please Enter Total", Toast.LENGTH_SHORT).show();
                    else {
                        Log.d(TAG, "check" + " " + t + " " + p);
                        if (p > t) {
                            Toast.makeText(TotalInfo.this, "Present cannot be greater than Total", Toast.LENGTH_SHORT).show();
                        } else {

                            if (c.getCount() <= 0) {
                                DatabaseOpenHelper.insertData(TotalInfo.this, subjects[sub], t, p);
                            } else
                                DatabaseOpenHelper.updateData(TotalInfo.this, subjects[sub], t, p);

                        }

                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        ButtonFragmentTwo fragmentTwo = new ButtonFragmentTwo();
                        fragmentTwo.setSubject(subjects[sub]);
                        fragmentTransaction.replace(R.id.frame_layout, fragmentTwo, null);
                        fragmentTransaction.commit();
                    }

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.create();
        builder.show();
    }


    public void editTimeTableDialog(){


    Intent in = new Intent(this,MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);

    }

}


