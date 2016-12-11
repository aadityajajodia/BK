package jajodia.aditya.com.bunkmanager2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class TotalInfo extends AppCompatActivity {

    private static final String TAG = "TotalInfo";

    //   MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_info);

        int size = MainActivity.getsize();
        Log.d(TAG,"size"+size);// no. of subjects
        String subject = MainActivity.getSubjects(); // subjects as a String separated by $
        String subjects[] = new String[size];
        int l = subject.length();
        int j=0;
        int k=0;
        for(int i=0;i<l;i++){
            if(subject.charAt(i)=='$'){
                subjects[k++] = subject.substring(j,i);
                j=i+1;
            }
        }
        LinearLayout li = (LinearLayout)findViewById(R.id.activity_total_info);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btn[] = new Button[size];
        for(int i=0;i<size;i++){
            btn[i] = new Button(this);
            Log.d(TAG,"size :"+size);
            btn[i].setText(subjects[i]);
            btn[i].setId(i);
            btn[i].setLayoutParams(params);
            li.addView(btn[i]);
        }
    }
}
