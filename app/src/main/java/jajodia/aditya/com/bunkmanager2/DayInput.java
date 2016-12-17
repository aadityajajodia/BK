package jajodia.aditya.com.bunkmanager2;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayInput extends AppCompatActivity {


    public static final String FILE="FileTwo";
    TextView dayName , periodName , subjectName;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_input);


        dayName = (TextView)findViewById(R.id.dayname_text_view);
        //periodName = (TextView)findViewById(R.id.period_text_view);
        //subjectName = (TextView)findViewById(R.id.subject_name_text_view);

        //one = (Button)findViewById(R.id.btn_notify);
      Calendar calendar = Calendar.getInstance();

        int date = calendar.get(Calendar.DAY_OF_WEEK);

        int d=0;

        String dayofWeek="";
        switch(date){
            case Calendar.MONDAY : d=1;
                            dayofWeek="MONDAY";
                            break;
            case Calendar.TUESDAY : d=2;
                                    dayofWeek="TUESDAY";
                            break;
            case Calendar.WEDNESDAY : d=3;
                                    dayofWeek="WEDNESDAY";
                                break;
            case Calendar.THURSDAY : d=4;
                                    dayofWeek="THURSDAY";
                                break;
            case Calendar.FRIDAY : d=5;
                                dayofWeek="FRIDAY";
                            break;
            case Calendar.SATURDAY : d=6;
                                dayofWeek="SATURDAY";
                                break;
            case Calendar.SUNDAY : d=7;
                                dayofWeek="SUNDAY";
                            break;
        }

           // Cursor cursor = DatabaseOpenHelperTwo.readData(this, d);

            //cursor.moveToFirst();

            dayName.setText(dayofWeek);


        SharedPreferences preferences = getSharedPreferences(FILE,0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("DAY",d);

        editor.commit();

        FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();

            FragmentDayInput fragmentDayInput = new FragmentDayInput();

                fragmentDayInput.setDay(d);
                fragmentDayInput.setPeriod(1);
                transaction.replace(R.id.frame_container, fragmentDayInput, null);
                transaction.commit();

        }


    }



