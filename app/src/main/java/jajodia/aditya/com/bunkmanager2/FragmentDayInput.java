package jajodia.aditya.com.bunkmanager2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by kunalsingh on 16/12/16.
 */

public class FragmentDayInput extends Fragment {

    Spinner spinner ;
    int day;
    View view;
    int rbint =-1;
    String subject;
    String subjectReplaced="";
    public static final String TAG="FragmentDayInput";

    int period;
    public void setDay(int day){
        this.day = day;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public FragmentDayInput() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.day_fragment, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences(DayInput.FILE,0);

        day = preferences.getInt("DAY",0);
        Log.d(TAG,"Day " +" "+day);
        Cursor cursor = DatabaseOpenHelperTwo.readData(getActivity(), day);

        cursor.moveToFirst();
        if (period > 8) {
           Intent i = new Intent(getActivity(),TotalInfo.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            NotificationReciever.makeNotifiaction(getActivity(),day);
        } else {
            TextView tv_period = (TextView) view.findViewById(R.id.tv_period);
            tv_period.setText("Period" + " " + period);

            FragmentManager manager = getFragmentManager();
            final FragmentTransaction transaction = manager.beginTransaction();
            final FragmentDayInput fragmentDayInput = new FragmentDayInput();
            subject = cursor.getString(period);
            Cursor cursor2 = DatabaseOpenHelperThree.readData(getActivity(),day);
            cursor2.moveToFirst();
            final int periods[] = new int[9];
            for(int i=0;i<8;i++) {
                periods[i] = cursor2.getInt(i);
            Log.d(TAG,"ccc"+" "+cursor2.getInt(i));
            }
                if (subject.isEmpty()||periods[period-1]==1) {
                fragmentDayInput.setPeriod(period + 1);
                transaction.replace(R.id.frame_container, fragmentDayInput, null);
                transaction.commit();
            }
            TextView tv_subject = (TextView) view.findViewById(R.id.tv_subject_cv);
            tv_subject.setText(subject);

            onClicked();

            spinner = (Spinner) view.findViewById(R.id.spinner);

            ArrayList<String> arrayList = new ArrayList<>();


            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.MY_FILE,0);
            String subjects = sharedPreferences.getString("Subjects","");
            int l =subjects.length();
            int j=0;
            for(int i=0;i<l;i++){
                if(subjects.charAt(i)=='$'){
                    String s = subjects.substring(j,i);
                    j=i+1;
                    if(!s.equals(subject))
                    arrayList.add(s);
                }
            }
           // Log.d(TAG,"came out of aray");
           // arrayList.add("check1");
            //arrayList.add("check2");
            //arrayList.add("check3");

            ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,arrayList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();
            spinner.setAdapter(adapter);
            Button btn = (Button) view.findViewById(R.id.btn_cv_done);


            onClickedSpinner();
            boolean status = false;
            Cursor cursor1 = DatabaseOpenHelper.readData(getActivity(), subject);
            cursor1.moveToFirst();
            int present = 0;
            int total = 0;

            if (cursor1.getCount() <= 0) {
                status = true;
            } else {
                total = cursor1.getInt(2);

                present = cursor1.getInt(3);
            }
            final boolean finalStatus = status;
            final int finalTotal = total;
            final int finalPresent = present;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    periods[period-1] = 1;
                    DatabaseOpenHelperThree.upgradeData(getActivity(),day,periods[0],periods[1],periods[2],periods[3],periods[4],periods[5],periods[6],periods[7]);
                    NotificationReciever.makeNotifiaction(getActivity(),day);
                    switch (rbint) {

                        case 0:
                            Log.d(TAG, "came in attended");
                            if (finalStatus) {
                                DatabaseOpenHelper.insertData(getActivity(), subject, 1, 1);
                            } else
                                DatabaseOpenHelper.updateData(getActivity(), subject, finalTotal + 1, finalPresent + 1);
                            break;
                        case 1:
                            Log.d(TAG, "came in bunked");
                            if (finalStatus) {
                                DatabaseOpenHelper.insertData(getActivity(), subject, 1, 0);
                            } else {
                                DatabaseOpenHelper.updateData(getActivity(), subject, finalTotal + 1, finalPresent);
                            }
                            break;
                        case 2:
                            Log.d(TAG, "came in cancelled");
                            break;
                        case 3:
                            Log.d(TAG, "came in shifted");


                            Cursor c = DatabaseOpenHelper.readData(getActivity(),subjectReplaced);
                            c.moveToFirst();
                            if(c.getCount()<=0){

                                DatabaseOpenHelper.insertData(getActivity(),subjectReplaced,1,1);
                            }else {
                                int t = c.getInt(2);
                                int p = c.getInt(3);

                                DatabaseOpenHelper.updateData(getActivity(), subjectReplaced, t + 1, p + 1);
                            }
                            break;

                    }

                    fragmentDayInput.setPeriod(period + 1);
                    transaction.replace(R.id.frame_container, fragmentDayInput, null);
                    transaction.commit();

                }
            });


        }
        return view;
    }

    private void onClickedSpinner() {


       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              String s = (String) parent.getItemAtPosition(position);
                Log.d(TAG,"selected" +" "+s);
           subjectReplaced = s;
               ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

    }

    public void onClicked(){


        final RadioButton shifted = (RadioButton)view.findViewById(R.id.rb_replaced);

        final RadioButton radioButtonAttended = (RadioButton) view.findViewById(R.id.rb_attended);

        final RadioButton cancelled = ((RadioButton)view.findViewById(R.id.rb_cancelled));

        final RadioButton bunked = (RadioButton)view.findViewById(R.id.rb_bunked);

        shifted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                radioButtonAttended.setChecked(false);
                cancelled.setChecked(false);
                bunked.setChecked(false);
                rbint = 3;
            }
        });


        radioButtonAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              spinner.setVisibility(View.INVISIBLE);
                shifted.setChecked(false);
                rbint = 0;


            }
        });

                cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               spinner.setVisibility(View.INVISIBLE);
                shifted.setChecked(false);

                rbint = 2;
            }
        });


        bunked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.INVISIBLE);
                shifted.setChecked(false);

                rbint = 1;
            }
        });



    }
}
