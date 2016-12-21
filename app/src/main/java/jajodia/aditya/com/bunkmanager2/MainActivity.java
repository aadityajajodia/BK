package jajodia.aditya.com.bunkmanager2;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final int SEARCH_REQUEST_CODE = 0;
    private static final String TAG = "MainActivity";
    EditText timeTable[][] = new EditText[6][8];
    HashMap<String, Integer> map; // no. of periods of the given subject
    String periods[][] = new String[6][8]; // actual period
    Button btn;
    static String subjects = "";
    public static final String MY_FILE = "TimeTableInput";

    //MainActivity mainActivity = new MainActivity();
    static int size;
boolean exit = false;
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_FILE, 0);

            setTheme(R.style.ThemeOne);

        infoAlertDialog();

            // getWindow().setEnterTransition(new Explode());

            // displaySpeechRecognizer();


            //Log.d(TAG,"not working");

            btn = (Button) findViewById(R.id.btn_done);

            // btn.setBackgroundColor(getResources().getColor(R.color.btn_color));


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map = new HashMap<String, Integer>();
                    assignTable();
                    readTable();
                    size = map.size();
                    if (size == 0) {
                        Toast.makeText(MainActivity.this, "Please enter the subjects", Toast.LENGTH_SHORT).show();

                    } else {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("You have a total of" + " " + size + " " + "subjects");
                        //  builder.setCancelable(true);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {




                                Log.d(TAG, "coming hone");
                                SharedPreferences sharedPreferences = getSharedPreferences(MY_FILE, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Done", true);
                                editor.putString("Subjects", getSubjects());
                                editor.putInt("Size", size);
                                editor.commit();

                                setAlarm(8, 00, 00);


                                for (int i = 0; i < 6; i++) {
                                    long t = DatabaseOpenHelperTwo.insertData(MainActivity.this, i + 1, periods[i][0], periods[i][1], periods[i][2], periods[i][3], periods[i][4], periods[i][5], periods[i][6], periods[i][7]);
                                    Log.d(TAG, "Row" + " " + t);
                                }
                                Intent in= new Intent(MainActivity.this, TotalInfo.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(in);

                            }


                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        builder.create();
                        builder.show();


                    }


                }
            });

        }


    public void assignTable() {

        timeTable[0][0] = (EditText) findViewById(R.id.tr_one_et_one);
        //timeTable[0][0].setWidth();
        timeTable[0][1] = (EditText) findViewById(R.id.tr_one_et_two);
        timeTable[0][2] = (EditText) findViewById(R.id.tr_one_et_three);
        timeTable[0][3] = (EditText) findViewById(R.id.tr_one_et_four);
        timeTable[0][4] = (EditText) findViewById(R.id.tr_one_et_five);
        timeTable[0][5] = (EditText) findViewById(R.id.tr_one_et_six);
        timeTable[0][6] = (EditText) findViewById(R.id.tr_one_et_seven);
        timeTable[0][7] = (EditText) findViewById(R.id.tr_one_et_eight);
        timeTable[1][0] = (EditText) findViewById(R.id.tr_two_et_one);
        timeTable[1][1] = (EditText) findViewById(R.id.tr_two_et_two);
        timeTable[1][2] = (EditText) findViewById(R.id.tr_two_et_three);
        timeTable[1][3] = (EditText) findViewById(R.id.tr_two_et_four);
        timeTable[1][4] = (EditText) findViewById(R.id.tr_two_et_five);
        timeTable[1][5] = (EditText) findViewById(R.id.tr_two_et_six);
        timeTable[1][6] = (EditText) findViewById(R.id.tr_two_et_seven);
        timeTable[1][7] = (EditText) findViewById(R.id.tr_two_et_eight);
        timeTable[2][0] = (EditText) findViewById(R.id.tr_three_et_one);
        timeTable[2][1] = (EditText) findViewById(R.id.tr_three_et_two);
        timeTable[2][2] = (EditText) findViewById(R.id.tr_three_et_three);
        timeTable[2][3] = (EditText) findViewById(R.id.tr_three_et_four);
        timeTable[2][4] = (EditText) findViewById(R.id.tr_three_et_five);
        timeTable[2][5] = (EditText) findViewById(R.id.tr_three_et_six);
        timeTable[2][6] = (EditText) findViewById(R.id.tr_three_et_seven);
        timeTable[2][7] = (EditText) findViewById(R.id.tr_three_et_eight);
        timeTable[3][0] = (EditText) findViewById(R.id.tr_four_et_one);
        timeTable[3][1] = (EditText) findViewById(R.id.tr_four_et_two);
        timeTable[3][2] = (EditText) findViewById(R.id.tr_four_et_three);
        timeTable[3][3] = (EditText) findViewById(R.id.tr_four_et_four);
        timeTable[3][4] = (EditText) findViewById(R.id.tr_four_et_five);
        timeTable[3][5] = (EditText) findViewById(R.id.tr_four_et_six);
        timeTable[3][6] = (EditText) findViewById(R.id.tr_four_et_seven);
        timeTable[3][7] = (EditText) findViewById(R.id.tr_four_et_eight);
        timeTable[4][0] = (EditText) findViewById(R.id.tr_five_et_one);
        timeTable[4][1] = (EditText) findViewById(R.id.tr_five_et_two);
        timeTable[4][2] = (EditText) findViewById(R.id.tr_five_et_three);
        timeTable[4][3] = (EditText) findViewById(R.id.tr_five_et_four);
        timeTable[4][4] = (EditText) findViewById(R.id.tr_five_et_five);
        timeTable[4][5] = (EditText) findViewById(R.id.tr_five_et_six);
        timeTable[4][6] = (EditText) findViewById(R.id.tr_five_et_seven);
        timeTable[4][7] = (EditText) findViewById(R.id.tr_five_et_eight);
        timeTable[5][0] = (EditText) findViewById(R.id.tr_six_et_one);
        timeTable[5][1] = (EditText) findViewById(R.id.tr_six_et_two);
        timeTable[5][2] = (EditText) findViewById(R.id.tr_six_et_three);
        timeTable[5][3] = (EditText) findViewById(R.id.tr_six_et_four);
        timeTable[5][4] = (EditText) findViewById(R.id.tr_six_et_five);
        timeTable[5][5] = (EditText) findViewById(R.id.tr_six_et_six);
        timeTable[5][6] = (EditText) findViewById(R.id.tr_six_et_seven);
        timeTable[5][7] = (EditText) findViewById(R.id.tr_six_et_eight);

    }


    public void readTable() {

        subjects = "";

        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 8; j++) {
                String s="";
                if (timeTable[i][j].getText() != null){

                    s = String.valueOf(timeTable[i][j].getText()).toUpperCase();
            }

                if (map.containsKey(s)) {

                    int d = map.get(s);
                    map.remove(s);
                    map.put(s, ++d);
                } else if (!s.isEmpty()) {
                    map.put(s, 1);
                    subjects = subjects + s + "$";
                }
                periods[i][j] = s;
            }


           // Log.d(TAG,"Database Two : "+t);
        }
    }

    public static int getsize() {

        return size;
    }

    public static String getSubjects() {
        return subjects;
    }


    public void setAlarm(int hr , int min , int sec) {


        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,sec);
        Intent intent4 = new Intent(getApplicationContext(),NotificationReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),50,intent4,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

public  void displaySpeechRecognizer(){
    Intent intent  =  new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

    startActivityForResult(intent,SEARCH_REQUEST_CODE);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==SEARCH_REQUEST_CODE&&resultCode==RESULT_OK){
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            Log.d(TAG,results.get(0));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void infoAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setTitle("Please fill the time table");

        builder.setMessage("Leave blank the periods and days you have no class");

        builder.setCancelable(true);

        builder.create();

        builder.show();
    }

}