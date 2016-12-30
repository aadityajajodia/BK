package jajodia.aditya.com.tickernotify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TotalInfo extends FragmentActivity {


    InterstitialAd interstitialAd;
    public double minPerc=75.0;
    private static final String TAG = "TotalInfo";
    public static final String MY_PERC="MyPerc";
    RelativeLayout li;
    int size;
    static String subjects[];
    WindowManager windowManager ;
    SharedPreferences sharedPreferences ;
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

            case R.id.item_view_tt :Intent int2 = new Intent(this,ViewTimeTable.class);
                                    startActivity(int2);
                                    break;

            case R.id.item_edit : editAlertDialog();
                                    break;

            case R.id.share : Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                String shareBody = "Hello there!\n" +
                                        "Had trouble managing your attendance last semester? \n" +
                                        "Well not any more! \n" +
                                        "Try TICKER. \n" +
                                        "Your one stop solution for managing the much needed attendance.  \n" +
                                        "Just keep the app updated with the number of lectures you attend and there you go, TICKER is ready with all the necessary statistics you need to be regular!";
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Please download the app");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody+"\n\n"+"https://play.google.com/store/apps/details?id="+getPackageName());
                                startActivity(Intent.createChooser(shareIntent,"Share Via"));
                                break;


            case R.id.contact_us : Intent intent1 = new Intent(this,ContactUsActivity.class);
                                    startActivity(intent1);
                                    break;
            case R.id.item_info : Intent in2 = new Intent(this,InfoActivity.class);
                                        startActivity(in2);
                                        break;
            case R.id.reset_item : resetAlertDialog();
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

        sharedPreferences = getSharedPreferences(MY_PERC,0);
        minPerc = sharedPreferences.getFloat("minPerc",75);

        Intent in = getIntent();
        boolean bol = in.getBooleanExtra("STATUS",false);
        if(bol) {
            Log.d(TAG,"came to exit notification");
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(50);

        }
            //     ActionBar actionBar = getSupportActionBar();


            //showing interstitial add

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3993264348115134/1188845600");//ca-app-pub-3993264348115134/1188845600
        requesttNewInterstitial();
        Intent intent4 = getIntent();
        boolean sta = intent4.getBooleanExtra("ADD",false);
        if(sta){
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    interstitialAd.show();

                }
            });
        }

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
                btn[i].setBackgroundColor(Color.parseColor("#03a9f4"));

                final int w = getWidth();
                Log.d(TAG, "width : " + w);
                params[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params[i].setMargins(4,4,0,0);

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

                                btn[finalI].setWidth(w/4);
                                if(subjects[finalI].length()<=5)
                                    btn[finalI].setText(subjects[finalI]);
                                else
                                    btn[finalI].setText(subjects[finalI].substring(0, 3) + "..");

                                return true;
                            case MotionEvent.ACTION_MOVE:
                                Log.d(TAG, "came in case 1");

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

                                    if (c.getInt(2) == 0) {
                                        btn[finalI].setTextColor(Color.WHITE);
                                        ButtonFragment fragment = new ButtonFragment();
                                        fragment.setSubject(subjects[finalI]);
                                        //   Log.d(TAG,"button called"+" "+finalI);
                                        fragmentTransaction.replace(R.id.frame_layout, fragment, null);
                                        fragmentTransaction.commit();
                                    } else {
                                        boolean b = percentattendance(subjects[finalI]);
                                        if (b)
                                            btn[finalI].setTextColor(Color.RED);
                                        else {
                                            btn[finalI].setTextColor(Color.WHITE);
                                        }
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//
                                        Log.d(TAG, "came in 2");
                                        ButtonFragmentTwo fragmentTwo = new ButtonFragmentTwo();
                                        fragmentTwo.setSubject(subjects[finalI]);
                                        fragmentTransaction.replace(R.id.frame_layout, fragmentTwo, null);
                                        fragmentTransaction.commit();
                                    }
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

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3993264348115134~2174554401");

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

            if(t==0)
                return false;

            float att = ((float)p/t)*100;//

            if(att>=minPerc)
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
        String [] edit = {"Edit Attendance","Edit TimeTable","Edit Minimum Attendance"};

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
                        case 2 : editMinAttendacePerc();
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

                return false;
            }
        });
        builder.setView(view);

        final EditText et_t = (EditText)view.findViewById(R.id.et_total_alert);
        final EditText et_p = (EditText)view.findViewById(R.id.et_present_alert);

        final Cursor c = DatabaseOpenHelper.readData(TotalInfo.this,subjects[sub]);
        c.moveToFirst();
        if(c.getCount()>0) {
            et_t.setText(String.valueOf(c.getInt(2)));
            et_p.setText(String.valueOf(c.getInt(3)));

           // et_t.setHintTextColor(Color.parseColor("#000080"));
            //et_p.setHintTextColor(Color.parseColor("#000080"));
        }else {
            et_t.setText("0");
            et_p.setText("0");
        }
            builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(et_p.getText().toString().isEmpty()||et_t.getText().toString().isEmpty())
                    Toast.makeText(TotalInfo.this, "Please enter new total and present ", Toast.LENGTH_SHORT).show();
                else {
                    int t = Integer.parseInt((et_t.getText().toString()));
                    int p = Integer.parseInt((et_p.getText().toString()));
                    if(t==0) {
                        Cursor cursor = DatabaseOpenHelper.readData(TotalInfo.this,subjects[sub]);
                        if(cursor.getCount()>0){
                            DatabaseOpenHelper.updateData(TotalInfo.this,subjects[sub],0,0);
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ButtonFragment fragment = new ButtonFragment();
                        fragment.setSubject(subjects[sub]);
                        //   Log.d(TAG,"button called"+" "+finalI);
                        fragmentTransaction.replace(R.id.frame_layout, fragment, null);
                        fragmentTransaction.commit();


                    }
                        else {
                        Log.d(TAG, "check" + " " + t + " " + p);
                        if (p > t) {
                            Toast.makeText(TotalInfo.this, "Present cannot be greater than Total", Toast.LENGTH_SHORT).show();
                        } else {

                            if (c.getCount() <= 0) {
                                DatabaseOpenHelper.insertData(TotalInfo.this, subjects[sub], t, p);
                            } else
                                DatabaseOpenHelper.updateData(TotalInfo.this, subjects[sub], t, p);


                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = manager.beginTransaction();
                            ButtonFragmentTwo fragmentTwo = new ButtonFragmentTwo();
                            fragmentTwo.setSubject(subjects[sub]);
                            fragmentTransaction.replace(R.id.frame_layout, fragmentTwo, null);
                            fragmentTransaction.commit();//
                        }
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

    public void editMinAttendacePerc(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter minimum required attendance");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_min_perc_alert,null);

        builder.setView(view);

        final EditText editText = (EditText)view.findViewById(R.id.min_perc);

        editText.setHint(String.valueOf(minPerc)+"%");

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (editText.getText().toString().isEmpty()){

                }
                else {
                    float newperc = Float.parseFloat(editText.getText().toString());

                    if (newperc == 0) {
                        Toast.makeText(TotalInfo.this, "Minimum percentage cannot be zero", Toast.LENGTH_SHORT).show();
                    } else {
                        minPerc = newperc;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat("minPerc", newperc);
                        editor.commit();
                    }
                }
                Toast.makeText(TotalInfo.this, "Minimun Percentage is set to" + " " + minPerc+"%", Toast.LENGTH_SHORT).show();

            }

        });

        builder.create();
        builder.show();

    }

    public void resetAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String ch[] = {"Reset Subject Attendance","Reset Time Table"};


        builder.setCancelable(false);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_BACK)
                    dialog.cancel();

                return false;
            }
        });

        builder.setItems(ch, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(TotalInfo.this);
                builder1.setTitle("WARNING");
                builder1.setCancelable(false);
                builder1.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        if(keyCode==KeyEvent.KEYCODE_BACK)
                            dialog.cancel();

                        return false;
                    }
                });
                builder1.setIcon(R.drawable.ic_warning_black_24dp);
                switch (which){

                    case 0:builder1.setMessage("Are you sure you want to delete the attendance data for all subjects");
                            builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseOpenHelper.deleteAllRows(TotalInfo.this);
                                    Intent in = new Intent(TotalInfo.this,TotalInfo.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(in);
                                }
                            });
                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder1.create();
                        builder1.show();
                            break;
                    case 1: builder1.setMessage("Are you sure want to reset the time table" );
                            builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseOpenHelperTwo.deleteAllRows(TotalInfo.this);
                                    DatabaseOpenHelperThree.deleteAllRows(TotalInfo.this);
                                    HashMap<String , Integer>map1 = new HashMap<>();
                                    try {
                                        FileInputStream fileInputStream = new FileInputStream(getApplicationContext().getFilesDir()+"/HashMap.ser");
                                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                                        map1 = (HashMap)objectInputStream.readObject();
                                        objectInputStream.close();
                                    }catch(Exception e){

                                    }
                                    Set<String> set = new HashSet<String>();
                                    for(Object key : map1.keySet()){
                                        String match = key.toString();
                                        set.add(match);
                                    }
                                    map1.keySet().removeAll(set);
                                    try{
                                        FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("HashMap.ser",Context.MODE_PRIVATE);
                                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                                        objectOutputStream.writeObject(map1);
                                        objectOutputStream.close();
                                    }catch (Exception e){

                                    }
                                    NotificationManager manager = (NotificationManager) TotalInfo.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    manager.cancel(50);
                                    Intent in = new Intent(TotalInfo.this,TotalInfo.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(in);
                                }
                            });

                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder1.create();
                        builder1.show();

                        break;

                }

            }
        });

        builder.create();
        builder.show();
    }
    public void requesttNewInterstitial(){
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        interstitialAd.loadAd(adRequest);
    }
}


