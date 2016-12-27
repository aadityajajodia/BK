package jajodia.aditya.com.bunkmanager2;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_FILE, 0);

            setTheme(R.style.ThemeOne);

        infoAlertDialog();

        assignTable();

        Cursor cursor = DatabaseOpenHelperTwo.readAllRows(this);

        cursor.moveToFirst();
        boolean status2=true;
        if(cursor.getCount()>0){
            status2 = false;
            int j=0;
            do{
                Log.d(TAG,"came in  loop"+" "+j);

                for(int i=1;i<9;i++){
                    Log.d(TAG," " +cursor.getString(i));
                    timeTable[j][i-1].setText(cursor.getString(i));
                }
                j++;
            }while (cursor.moveToNext()&&j<=5);

        }
        Log.d(TAG,"came out of loop");
            btn = (Button) findViewById(R.id.btn_done);

            // btn.setBackgroundColor(getResources().getColor(R.color.btn_color));

        btn.setTextColor(Color.parseColor("#03a9f4"));
        final boolean finalStatus = status2;
        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map = new HashMap<String, Integer>();
                    readTable();
                    size = map.size();
                    if (size == 0) {
                        Toast.makeText(MainActivity.this, "Please enter the subjects", Toast.LENGTH_SHORT).show();

                    } else {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("You have a total of" + " " + size + " " + "subjects");

                        builder.setIcon(R.drawable.ic_notifications_none_black_24dp);

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

                                setAlarm(21, 00, 00);



                                for (int i = 0; i < 6; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        Log.d(TAG, " " + i + "  " + periods[i][j]);
                                    }
                                    long t;
                                    if (finalStatus) {
                                        t = DatabaseOpenHelperTwo.insertData(MainActivity.this, i + 1, periods[i][0], periods[i][1], periods[i][2], periods[i][3], periods[i][4], periods[i][5], periods[i][6], periods[i][7]);
                                        Log.d(TAG, "came in ifff");
                                    } else {
                                        t = DatabaseOpenHelperTwo.updateData(MainActivity.this, i + 1, periods[i][0], periods[i][1], periods[i][2], periods[i][3], periods[i][4], periods[i][5], periods[i][6], periods[i][7]);
                                        Log.d(TAG, "came in elseee");

                                        Log.d(TAG, "Row" + " " + t);
                                    }
                                }
                                Calendar calendar = Calendar.getInstance();
                                int hr = calendar.get(Calendar.HOUR_OF_DAY);
                                int day = DayInput.setDate();
                                if(hr>=21) {
                                    NotificationReciever.makeNotifiaction(MainActivity.this, day);
                                }
                                Intent in = new Intent(MainActivity.this, TotalInfo.class);
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
        for(int i=0;i<6;i++){
            for(int j=0;j<8;j++){
                timeTable[i][j].setInputType(InputType.TYPE_CLASS_TEXT);

            }
        }

    }


    public void readTable() {

        subjects = "";


        for (int i = 0; i < 6; i++) {
            Cursor cursor = DatabaseOpenHelperThree.readData(this,i+1);
            for (int j = 0; j < 8; j++) {
                String s="";
                if (timeTable[i][j].getText() != null){
                    Log.d(TAG,"checking string "+timeTable[i][j].getText().toString()+j);
                    if(timeTable[i][j].getText().toString().equals("\n")){
                        Log.d(TAG,"came in line ");
                    }else {
                        s = String.valueOf(timeTable[i][j].getText()).toUpperCase();
                    }
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


            //Log.d(TAG,"readTable "+ " "+t);
            }
            cursor.moveToFirst();
            if(cursor.getCount()<=0) {
                long t = DatabaseOpenHelperThree.insertData(this, i + 1, 0, 0, 0, 0, 0, 0, 0, 0);
            }


            // Log.d(TAG,"Database Two : "+t);
        }
            HashMap<String , Integer>map1 = new HashMap<>();
        try {
            Log.d(TAG,"came in try1");
            FileInputStream fileInputStream = new FileInputStream(getApplicationContext().getFilesDir()+"/HashMap.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map1 = (HashMap)objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            try {
                Log.d(TAG,"came in try2");
                FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("HashMap.ser",Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(map);
                objectOutputStream.close();
            } catch (Exception e2) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Object key : map1.keySet()){

            Log.d(TAG,"came in delete row");
            String match = key.toString();
            Log.d(TAG,match);
            if(!map.containsKey(match)) {

                for (int i = 0; i < 6; i++) {
                    Cursor cursor2 = DatabaseOpenHelperTwo.readData(this, i + 1);
                    Cursor cursor3 = DatabaseOpenHelperThree.readData(this, i + 1);
                    cursor3.moveToFirst();
                    cursor2.moveToFirst();
                    if (!(cursor2.getCount() <= 0 || cursor3.getCount() <= 0)) {
                        int st[] = new int[8];
                        String s[] = new String[8];
                        for (int j = 0; j < 8; j++) {
                            Log.d(TAG, "see " + j);
                            s[j] = cursor2.getString(j + 1);
                            st[j] = cursor3.getInt(j);
                            if (s[j].equals(match))
                                st[j] = 0;
                        }

                        DatabaseOpenHelperThree.upgradeData(this, i + 1, st[0], st[1], st[2], st[3], st[4], st[5], st[6], st[7]);

                    }

                    DatabaseOpenHelper.deleteRow(this, key.toString());
                }
            }
        }
        try {
            Log.d(TAG,"came in try3");
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("HashMap.ser", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
        }catch (Exception e){

        }
    }

    public static int getsize() {

        return size;
    }

    public static String getSubjects() {
        return subjects;
    }


    public void setAlarm(int hr , int min , int sec) {


        Log.d(TAG,"came in alarm");
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
    //as

        builder.show();
    }

}